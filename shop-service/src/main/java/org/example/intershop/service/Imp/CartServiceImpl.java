package org.example.intershop.service.Imp;

import org.example.intershop.DTO.OrderDto;
import org.example.intershop.client.api.PaymentApi;
import org.example.intershop.client.model.BalanceResponse;
import org.example.intershop.client.model.PaymentRequest;
import org.example.intershop.models.entity.Order;

import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private PositionRepo positionRepo;
    @Autowired
    private OrderRepo orderRepo;


    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentApi paymentService;

    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private PaymentApi paymentApi;
    @Autowired
    private SecurityService securityService;


    @Override
    public Mono<Long> pay() {
        return securityService.getCurrentUserId()
                .flatMap(userId ->
                        orderRepo.findByUserIdAndStatusFalse(userId)
                                .switchIfEmpty(Mono.error(
                                        new IllegalStateException("Order not found")
                                ))
                                .flatMap(order ->
                                        getCartTotalSum(order.getId())
                                                .flatMap(totalSum ->
                                                        paymentService.getBalance()
                                                                .map(BalanceResponse::getBalance)
                                                                .flatMap(balance -> {
                                                                    if (balance.compareTo(totalSum) < 0) {
                                                                        return Mono.error(
                                                                                new IllegalStateException("Not enough money")
                                                                        );
                                                                    }
                                                                    return paymentService.makePayment(
                                                                            new PaymentRequest().sum(totalSum)
                                                                    );
                                                                })
                                                                .thenReturn(totalSum)
                                                )
                                                .flatMap(totalSum -> {

                                                    // ✅ закрываем текущий заказ
                                                    order.setStatus(true);
                                                    order.setTotalPrice(totalSum.intValueExact());
                                                    order.setUserId(userId);

                                                    return orderRepo.save(order)
                                                            .flatMap(savedOrder ->
                                                                    positionRepo.findAllByOrderId(savedOrder.getId())
                                                                            .flatMap(position -> {
                                                                                position.setStatus(true);
                                                                                return positionRepo.save(position);
                                                                            })
                                                                            .then(Mono.just(savedOrder.getId()))
                                                            );
                                                })
                                )
                );
    }


    @Override
    public Mono<OrderDto> getBin() {
        return positionRepo.findAllByStatusFalse()
                .flatMap(position ->
                        itemService.getItemById(position.getItemId())
                                .map(itemDto -> {
                                    itemDto.setCount(position.getQuantity());
                                    itemDto.setPositionID(position.getId());
                                    return itemDto;
                                })
                )
                .collectList()
                .map(itemDtos -> {
                    OrderDto dto = new OrderDto();
                    dto.setItems(itemDtos);
                    return dto;
                });
    }


    @Override
    public Mono<BigDecimal> getBalance() {
        return oAuth2Service
                .getTokenValue()
                .flatMap(accessToken -> {
                    paymentApi.getApiClient().addDefaultHeader("Authorization", "Bearer " + accessToken);
                    return paymentApi.getBalance();
                })
                .map(BalanceResponse::getBalance)
                .onErrorResume(error -> {
                    System.out.println("Ошибка при обращении в платежный сервис: {}" + error.getMessage() + error);
                    return Mono.just(BigDecimal.ONE.negate());
                });
    }


    private Mono<BigDecimal> getCartTotalSum(Long orderId) {
        Mono<OrderDto> dto = orderService.findOrderById(1L);


        return positionRepo.findAllByOrderId(orderId)
                .flatMap(position ->
                        itemRepo.findById(position.getItemId())
                                .map(item ->
                                        BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(position.getQuantity())
                                        ))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}

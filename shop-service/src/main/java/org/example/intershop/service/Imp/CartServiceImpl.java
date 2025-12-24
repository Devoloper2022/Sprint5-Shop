package org.example.intershop.service.Imp;

import org.example.intershop.DTO.OrderDto;
import org.example.intershop.client.api.PaymentApi;
import org.example.intershop.client.model.BalanceResponse;
import org.example.intershop.client.model.PaymentRequest;
import org.example.intershop.models.entity.Order;

import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.CartService;
import org.example.intershop.service.ItemService;
import org.example.intershop.service.OrderService;
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


    @Override
    public Mono<Long> pay() {
//        return orderRepo.findByIdAndStatusFalse(1L)
//                .switchIfEmpty(Mono.error(new IllegalStateException("Order not found")))
//                .flatMap(oldOrder -> {
//                    OrderEntity newEntity = new OrderEntity();
//                    newEntity.setStatus(true);
//
//                    return orderRepo.save(newEntity)
//                            .flatMap(savedOrder ->
//                                    positionRepo.findAllByOrderId(oldOrder.getId())
//                                            .flatMap(position -> {
//                                                position.setOrderId(savedOrder.getId());
//                                                position.setStatus(true);
//                                                return positionRepo.save(position);
//                                            })
//                                            .then(Mono.just(savedOrder.getId()))
//                            );
//                });


        return orderRepo.findByIdAndStatusFalse(1L)
                .switchIfEmpty(Mono.error(new IllegalStateException("Order not found")))
                .flatMap(oldOrder ->
                        getCartTotalSum(1l)
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
                                .flatMap(sum -> {
                                    Order newEntity = new Order();
                                    newEntity.setStatus(true);

                                    return orderRepo.save(newEntity)
                                            .flatMap(savedOrder ->
                                                    positionRepo.findAllByOrderId(oldOrder.getId())
                                                            .flatMap(position -> {
                                                                position.setOrderId(savedOrder.getId());
                                                                position.setStatus(true);
                                                                return positionRepo.save(position);
                                                            })
                                                            .then(Mono.just(savedOrder.getId()))
                                            );
                                })
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

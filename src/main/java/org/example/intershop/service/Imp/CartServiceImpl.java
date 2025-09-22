package org.example.intershop.service.Imp;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.models.entity.OrderEntity;
import org.example.intershop.models.entity.Position;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.CartService;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private PositionRepo positionRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ItemService itemService;


    @Override
    public Mono<Long> pay() {
        return orderRepo.findByIdAndStatusFalse(1L)
                .switchIfEmpty(Mono.error(new IllegalStateException("Order not found")))
                .flatMap(oldOrder -> {
                    OrderEntity newEntity = new OrderEntity();
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
                });
    }


    @Override
    public Mono<OrderDto>  getBin() {
        return positionRepo.findAllByStatusFalse() // Flux<Position>
                .flatMap(position ->
                        itemService.getItemById(position.getItemId()) // Mono<ItemDto>
                                .map(itemDto -> {
                                    itemDto.setCount(position.getQuantity());
                                    itemDto.setPositionID(position.getId());
                                    return itemDto;
                                })
                )
                .collectList() // Mono<List<ItemDto>>
                .map(itemDtos -> {
                    OrderDto dto = new OrderDto();
                    dto.setItems(itemDtos);
                    return dto;
                });
    }


}

package org.example.intershop.service;

import org.example.intershop.DTO.OrderDto;
import org.example.intershop.DTO.OrderHistoryDto;
import reactor.core.publisher.Mono;


public interface OrderService {


    Mono<OrderHistoryDto> findOrders();

    Mono<OrderDto> findOrderById(Long orderId);

    Mono<Void> addPosition( Long itemId);

    Mono<Void> removePosition(Long positionId);

    Mono<Void> incrementPosition(Long ItemId);

    Mono<Void> decrementPosition(Long ItemId);
}

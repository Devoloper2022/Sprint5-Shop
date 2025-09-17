package org.example.intershop.service;

import org.example.intershop.DTO.OrderDto;

import java.util.List;

public interface OrderService {
    Long createOrder();

    List<OrderDto> findOrders();

    OrderDto findOrderById(Long orderId);

    void addPosition(Long ItemId, Long OrderId);
    void removePosition(Long ItemId, Long OrderId);
}

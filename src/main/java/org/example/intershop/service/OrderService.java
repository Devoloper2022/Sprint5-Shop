package org.example.intershop.service;

import org.example.intershop.DTO.OrderDto;
import org.example.intershop.DTO.OrderHistoryDto;

import java.util.List;

public interface OrderService {
    Long createOrder();

    List<OrderHistoryDto> findOrders();

    OrderDto findOrderById(Long orderId);

    void addPosition(Long ItemId, Long OrderId);
    void removePosition(Long positionId);
    void incrementPosition(Long ItemId);
    void decrementPosition(Long ItemId);
}

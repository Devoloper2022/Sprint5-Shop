package org.example.intershop.service;

import org.example.intershop.DTO.OrderDto;
import org.example.intershop.DTO.OrderHistoryDto;


public interface OrderService {


    OrderHistoryDto findOrders();

    OrderDto findOrderById(Long orderId);

    void addPosition(Long orderId, Long itemId);
    void removePosition(Long positionId);
    void incrementPosition(Long ItemId);
    void decrementPosition(Long ItemId);
}

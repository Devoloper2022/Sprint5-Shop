package org.example.intershop.service;

import org.example.intershop.DTO.OrderDto;

public interface CartService {
    void pay();
    OrderDto getBin();
}

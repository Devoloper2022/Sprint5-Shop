package org.example.intershop.service;

import org.example.intershop.DTO.OrderDto;

public interface CartService {
    Long pay();
    OrderDto getBin();
}

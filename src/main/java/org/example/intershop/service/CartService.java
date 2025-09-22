package org.example.intershop.service;

import org.example.intershop.DTO.OrderDto;
import reactor.core.publisher.Mono;

public interface CartService {
    Mono<Long> pay();

    Mono<OrderDto>  getBin();
}

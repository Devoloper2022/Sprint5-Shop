package org.example.intershop.service;

import org.example.intershop.DTO.OrderDto;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface CartService {
    Mono<Long> pay();

    Mono<OrderDto> getBin();

    Mono<BigDecimal> getBalance();

}

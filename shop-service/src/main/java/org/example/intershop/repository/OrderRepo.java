package org.example.intershop.repository;

import org.example.intershop.models.entity.Order;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepo extends R2dbcRepository<Order, Long> {

    @Override
    Flux<Order> findAll();

    Mono<Order> findByIdAndStatusFalse(Long orderId);

    Mono<Order> findByUserIdAndStatusFalse(Long userId);

    Mono<Boolean> existsByIdAndStatusFalse(Long orderId);

    Mono<Boolean> existsByUserIdAndStatusFalse(Long userId);

    Flux<Order> findAllByStatusTrue();

    Flux<Order> findAllByUserIdAndStatusTrue(Long userId);

    Mono<Order> findByIdAndUserId(Long id, Long userId);
}
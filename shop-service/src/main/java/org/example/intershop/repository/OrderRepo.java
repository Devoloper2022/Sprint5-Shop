package org.example.intershop.repository;

import org.example.intershop.models.entity.OrderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepo extends R2dbcRepository<OrderEntity, Long> {

    @Override
    Flux<OrderEntity> findAll();

    Mono<OrderEntity> findByIdAndStatusFalse(Long orderId);

    Mono<Boolean> existsByIdAndStatusFalse(Long orderId);

    Flux<OrderEntity> findAllByStatusTrue();
}
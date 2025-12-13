package org.example.intershop.repository;

import org.example.intershop.models.entity.Position;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PositionRepo extends R2dbcRepository<Position, Long> {

    Flux<Position> findAllByOrderId(Long ordersId);

    Mono<Position> findByItemIdAndStatusFalse(Long itemId);


    Mono<Boolean> existsByItemIdAndStatusFalse(Long itemId);

    Flux<Position> findAllByStatusFalse();
}

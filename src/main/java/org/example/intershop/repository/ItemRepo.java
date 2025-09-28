package org.example.intershop.repository;

import org.example.intershop.models.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface ItemRepo extends R2dbcRepository<Item, Long> {


    Flux<Item> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Sort sort);


    Flux<Item> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title, String description, Pageable pageable
    );

    Flux<Item> findAll();

    Mono<Void> deleteById(Long id);

    @Override
    Mono<Item> findById(Long id);

}

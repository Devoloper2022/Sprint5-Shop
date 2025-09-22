package org.example.intershop.repository;

import org.example.intershop.models.entity.Item;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface ItemRepo extends R2dbcRepository<Item, Long> {

    @Query("""
                SELECT *
                FROM items
                WHERE (:search IS NULL OR LOWER(title) LIKE LOWER(CONCAT('%', :search, '%'))
                       OR LOWER(description) LIKE LOWER(CONCAT('%', :search, '%')))
                ORDER BY :sortColumn
                LIMIT :limit OFFSET :offset 
            """)
    Flux<Item> searchAll(@Param("search") String search,
                         @Param("limit") int limit,
                         @Param("offset") long offset,
                         @Param("sortColumn") String sortColumn);

    Mono<Void> deleteById(Long id);

    @Override
    Mono<Item> findById(Long id);

}

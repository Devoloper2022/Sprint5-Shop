package org.example.intershop.repository;

import org.example.intershop.models.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Optional;

public interface ItemRepo extends JpaRepository<Item,Long> {

    @Query("""
            SELECT item
            FROM Item item
            WHERE :search IS NULL
            OR item.title ILIKE %:search%
            OR item.description ILIKE %:search%
            """)
    Page<Item> searchAllPagingAndSorting(@Param("search") String search, Pageable pageable);

    void deleteById(@Param("id") Long id);

    @Override
    Optional<Item> findById(Long Long);
}

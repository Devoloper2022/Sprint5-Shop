package org.example.intershop.repository;

import org.example.intershop.models.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order,Long> {

    List<Order> findAll();

    @Override
    Optional<Order> findById(Long aLong);
}

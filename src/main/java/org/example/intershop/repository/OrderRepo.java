package org.example.intershop.repository;

import org.example.intershop.models.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<OrderEntity,Long> {

    List<OrderEntity> findAll();

    Optional<OrderEntity> findByIdAndStatusFalse(Long orderId);
    boolean existsByIdAndStatusFalse(Long orderId);
    List<OrderEntity> findAllByStatusTrue();

    @Override
    Optional<OrderEntity> findById(Long aLong);
}

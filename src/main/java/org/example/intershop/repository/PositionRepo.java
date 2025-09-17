package org.example.intershop.repository;

import org.example.intershop.models.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepo extends JpaRepository<Position,Long> {

    List<Position> findAllByOrderId(Long ordersId);
    List<Position> findAllByItemIdAndOrderId(Long userId, Long orderId);

    @Override
    List<Position> findAll();

    @Override
    Optional<Position> findById(Long aLong);
}

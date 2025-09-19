package org.example.intershop.repository;

import org.example.intershop.models.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepo extends JpaRepository<Position,Long> {

    List<Position> findAllByOrderId(Long ordersId);

    Optional<Position> findByItemIdAndStatusFalse(Long itemId);

    boolean existsByItemIdAndStatusFalse(Long itemId);

    List<Position> findAllByStatusFalse();

    void deleteById(Long id);




    @Override
    List<Position> findAll();

    @Override
    Optional<Position> findById(Long aLong);
}

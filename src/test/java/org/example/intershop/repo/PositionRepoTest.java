//package org.example.intershop.repo;
//
//import org.example.intershop.models.entity.Position;
//import org.example.intershop.repository.PositionRepo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@DataJpaTest
//public class PositionRepoTest {
//    @Autowired
//    private PositionRepo positionRepo;
//
//    @Test
//    void findAllByOrderId_returnsOnlyPositionsOfThatOrder() {
//        Position pos1 = new Position();
//        pos1.setOrderId(1L);
//        pos1.setItemId(100L);
//        pos1.setQuantity(2);
//        pos1.setStatus(false);
//        positionRepo.save(pos1);
//
//        Position pos2 = new Position();
//        pos2.setOrderId(2L);
//        pos2.setItemId(200L);
//        pos2.setQuantity(1);
//        pos2.setStatus(false);
//        positionRepo.save(pos2);
//
//        List<Position> result = positionRepo.findAllByOrderId(1L);
//
//        assertThat(result).hasSize(1);
//        assertThat(result.get(0).getOrderId()).isEqualTo(1L);
//    }
//
//    @Test
//    void findByItemIdAndStatusFalse_returnsInactivePosition() {
//        Position pos = new Position();
//        pos.setOrderId(1L);
//        pos.setItemId(123L);
//        pos.setQuantity(5);
//        pos.setStatus(false);
//        positionRepo.save(pos);
//
//        Optional<Position> result = positionRepo.findByItemIdAndStatusFalse(123L);
//
//        assertThat(result).isPresent();
//        assertThat(result.get().getItemId()).isEqualTo(123L);
//        assertThat(result.get().isStatus()).isFalse();
//    }
//
//    @Test
//    void existsByItemIdAndStatusFalse_returnsTrueWhenInactivePositionExists() {
//        Position pos = new Position();
//        pos.setOrderId(1L);
//        pos.setItemId(555L);
//        pos.setQuantity(3);
//        pos.setStatus(false);
//        positionRepo.save(pos);
//
//        boolean exists = positionRepo.existsByItemIdAndStatusFalse(555L);
//
//        assertThat(exists).isTrue();
//    }
//
//    @Test
//    void findAllByStatusFalse_returnsOnlyInactivePositions() {
//        Position inactive = new Position();
//        inactive.setOrderId(1L);
//        inactive.setItemId(999L);
//        inactive.setQuantity(1);
//        inactive.setStatus(false);
//        positionRepo.save(inactive);
//
//        Position active = new Position();
//        active.setOrderId(1L);
//        active.setItemId(888L);
//        active.setQuantity(1);
//        active.setStatus(true);
//        positionRepo.save(active);
//
//        List<Position> result = positionRepo.findAllByStatusFalse();
//
//        assertThat(result).hasSize(1);
//        assertThat(result.get(0).isStatus()).isFalse();
//    }
//}

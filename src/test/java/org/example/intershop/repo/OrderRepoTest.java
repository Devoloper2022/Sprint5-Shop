//package org.example.intershop.repo;
//
//import org.example.intershop.models.entity.OrderEntity;
//import org.example.intershop.repository.OrderRepo;
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
//public class OrderRepoTest {
//
//
//    @Autowired
//    private OrderRepo orderRepo;
//
//    @Test
//    void findByIdAndStatusFalse_returnsInactiveOrder() {
//
//        OrderEntity inactive = new OrderEntity();
//        inactive.setStatus(false);
//        inactive = orderRepo.save(inactive);
//
//        OrderEntity active = new OrderEntity();
//        active.setStatus(true);
//        orderRepo.save(active);
//
//
//        Optional<OrderEntity> result = orderRepo.findByIdAndStatusFalse(inactive.getId());
//
//
//        assertThat(result).isPresent();
//        assertThat(result.get().isStatus()).isFalse();
//    }
//
//    @Test
//    void existsByIdAndStatusFalse_returnsTrueForInactiveOrder() {
//        OrderEntity inactive = new OrderEntity();
//        inactive.setStatus(false);
//        inactive = orderRepo.save(inactive);
//
//        boolean exists = orderRepo.existsByIdAndStatusFalse(inactive.getId());
//
//        assertThat(exists).isTrue();
//    }
//
//    @Test
//    void findAllByStatusTrue_returnsOnlyActiveOrders() {
//        OrderEntity inactive = new OrderEntity();
//        inactive.setStatus(false);
//        orderRepo.save(inactive);
//
//        OrderEntity active = new OrderEntity();
//        active.setStatus(true);
//        orderRepo.save(active);
//
//        List<OrderEntity> result = orderRepo.findAllByStatusTrue();
//
//        assertThat(result).hasSize(1);
//        assertThat(result.get(0).isStatus()).isTrue();
//    }
//}

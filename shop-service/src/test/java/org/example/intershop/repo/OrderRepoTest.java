package org.example.intershop.repo;

import org.example.intershop.models.entity.Order;
import org.example.intershop.models.entity.OrderEntity;
import org.example.intershop.repository.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@DataR2dbcTest
public class OrderRepoTest {


    @Autowired
    private OrderRepo orderRepo;

    @BeforeEach
    void setup() {
        orderRepo.deleteAll()
                .thenMany(Flux.just(
                        createOrder(false),
                        createOrder(false),
                        createOrder(true)
                ).flatMap(orderRepo::save))
                .blockLast();
    }

    private OrderEntity createOrder(boolean status) {
        OrderEntity order = new OrderEntity();
        order.setStatus(status);
        return order;
    }

    @Test
    void testFindAll() {
        StepVerifier.create(orderRepo.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void testFindByIdAndStatusFalse() {
        Order order = new Order();
        order.setStatus(false);

        StepVerifier.create(orderRepo.save(order)
                        .flatMap(saved -> orderRepo.findByIdAndStatusFalse(saved.getId())))
                .expectNextMatches(found -> !found.isStatus())
                .verifyComplete();
    }

    @Test
    void testExistsByIdAndStatusFalse() {
        Order order = new Order();
        order.setStatus(false);

        StepVerifier.create(orderRepo.save(order)
                        .flatMap(saved -> orderRepo.existsByIdAndStatusFalse(saved.getId())))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void testFindAllByStatusTrue() {
        StepVerifier.create(orderRepo.findAllByStatusTrue())
                .expectNextMatches(Order::isStatus)
                .verifyComplete();
    }

}

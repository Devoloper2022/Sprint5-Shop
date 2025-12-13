package org.example.intershop.repo;

import org.example.intershop.models.entity.Position;
import org.example.intershop.repository.PositionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@DataR2dbcTest
public class PositionRepoTest {
    @Autowired
    private PositionRepo positionRepo;

    @BeforeEach
    void setup() {
        positionRepo.deleteAll()
                .thenMany(Flux.just(
                        createPosition(1L, 10L, false),
                        createPosition(1L, 11L, false),
                        createPosition(2L, 12L, true)
                ).flatMap(positionRepo::save))
                .blockLast();
    }

    private Position createPosition(Long orderId, Long itemId, boolean status) {
        Position p = new Position();
        p.setOrderId(orderId);
        p.setItemId(itemId);
        p.setStatus(status);
        return p;
    }

    @Test
    void testFindAllByOrderId() {
        StepVerifier.create(positionRepo.findAllByOrderId(1L))
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByItemIdAndStatusFalse() {
        StepVerifier.create(positionRepo.findByItemIdAndStatusFalse(10L))
                .expectNextMatches(pos -> pos.getItemId().equals(10L) && !pos.isStatus())
                .verifyComplete();
    }

    @Test
    void testExistsByItemIdAndStatusFalse() {
        StepVerifier.create(positionRepo.existsByItemIdAndStatusFalse(11L))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void testFindAllByStatusFalse() {
        StepVerifier.create(positionRepo.findAllByStatusFalse())
                .expectNextCount(2)
                .verifyComplete();
    }
}

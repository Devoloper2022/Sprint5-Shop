package org.example.intershop.repo;

import org.example.intershop.models.entity.Item;
import org.example.intershop.repository.ItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@DataR2dbcTest
public class ItemRepoTest {

    @Autowired
    private ItemRepo itemRepo;

    @BeforeEach
    void setup() {
        itemRepo.deleteAll()
                .thenMany(Flux.just(
                        new Item(null, "Apple", "Red juicy apple", "img1", 10L),
                        new Item(null, "Banana", "Yellow banana", "img2", 10L),
                        new Item(null, "Orange", "Sweet orange fruit", "img3", 10L)
                ).flatMap(itemRepo::save))
                .blockLast();
    }

    @Test
    void testFindByTitleOrDescription_withSort() {
        StepVerifier.create(
                        itemRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                                "apple", "apple", Sort.by("title").ascending()
                        )
                )
                .expectNextMatches(item -> item.getTitle().equals("Apple"))
                .verifyComplete();
    }

    @Test
    void testFindByTitleOrDescription_withPageable() {
        StepVerifier.create(
                        itemRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                                "a", "a", PageRequest.of(0, 2, Sort.by("title"))
                        )
                )
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        StepVerifier.create(itemRepo.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        Item saved = itemRepo.save(new Item(null, "Kiwi", "Green kiwi fruit", "img4", 10L)).block();

        StepVerifier.create(itemRepo.findById(saved.getId()))
                .expectNextMatches(item -> item.getTitle().equals("Kiwi"))
                .verifyComplete();
    }

    @Test
    void testDeleteById() {
        Item saved = itemRepo.save(new Item(null, "Mango", "Sweet mango", "img5", 10L)).block();

        StepVerifier.create(itemRepo.deleteById(saved.getId()))
                .verifyComplete();

        StepVerifier.create(itemRepo.findById(saved.getId()))
                .verifyComplete();
    }
}

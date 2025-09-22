package org.example.intershop.repo;

import org.example.intershop.models.entity.Item;
import org.example.intershop.repository.ItemRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ItemRepoTest {

    @Autowired
    private ItemRepo itemRepo;

    @Test
    void testSearchAllPagingAndSorting() {
        Item item1 = new Item(null, "Phone", "Smartphone", "phone.png", 1000L);
        Item item2 = new Item(null, "Tablet", "Android tablet", "tablet.png", 500L);

        itemRepo.save(item1);
        itemRepo.save(item2);

        Page<Item> result = itemRepo.searchAllPagingAndSorting("phone", PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("Phone", result.getContent().get(0).getTitle());
    }
}

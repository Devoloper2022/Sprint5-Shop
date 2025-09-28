package org.example.intershop.controller;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.SortType;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;


@WebFluxTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    PositionRepo positionRepo;
    @MockitoBean
    OrderRepo orderRepo;
    @MockitoBean
    ItemRepo itemRepo;

    @Test
    void testShowItems() {
        ItemDto item1 = new ItemDto();
        item1.setId(1L);
        item1.setTitle("Item 1");

        ItemDto item2 = new ItemDto();
        item2.setId(2L);
        item2.setTitle("Item 2");

        Mockito.when(itemService.findAllItemsPagingAndSorting(
                Mockito.anyString(),
                Mockito.any(SortType.class),
                Mockito.anyInt(),
                Mockito.anyInt()
        )).thenReturn(Flux.just(item1, item2));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/")
                        .queryParam("search", "test")
                        .queryParam("sort", "NO")
                        .queryParam("pageNumber", 1)
                        .queryParam("pageSize", 10)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String html = response.getResponseBody();
                    System.out.println(html);
                });
    }
}

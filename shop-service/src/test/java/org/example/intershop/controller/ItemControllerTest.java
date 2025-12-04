package org.example.intershop.controller;

import org.example.intershop.DTO.ItemDto;

import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;


import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;


@WebFluxTest(controllers = ItemController.class)
class ItemControllerTest {


    @Autowired
    private WebTestClient webTestClient;


    @MockitoBean
    ItemService itemService;

    @MockitoBean
    PositionRepo positionRepo;
    @MockitoBean
    OrderRepo orderRepo;

    @Test
    void testGetItemById() {
        ItemDto mockItem = new ItemDto();
        mockItem.setId(1L);
        mockItem.setTitle("Test Item");
        mockItem.setDescription("Test Description");

        Mockito.when(itemService.getItemById(1L))
                .thenReturn(Mono.just(mockItem));

        webTestClient.get()
                .uri("/items/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String body = response.getResponseBody();
                    System.out.println("Response = " + body);
                });
    }
}

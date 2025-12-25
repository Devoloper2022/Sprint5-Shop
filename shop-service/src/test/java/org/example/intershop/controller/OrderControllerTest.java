package org.example.intershop.controller;

import org.example.intershop.DTO.ActionForm;
import org.example.intershop.DTO.ActionType;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.DTO.OrderHistoryDto;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.OrderService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.jupiter.api.Test;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private OrderService orderService;
    @MockitoBean
    PositionRepo positionRepo;
    @MockitoBean
    OrderRepo orderRepo;
    @MockitoBean
    ItemRepo itemRepo;

    @Test
    void testCountItemPlus() {
        Mockito.when(orderService.incrementPosition(1L))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/orders/action/1")
                .bodyValue("action=" + ActionType.PLUS.getName())
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/");
    }

    @Test
    void testCountItemMinus() {
        Mockito.when(orderService.decrementPosition(1L))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/orders/action/1")
                .bodyValue("action=" + ActionType.MINUS.getName())
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/");
    }

    @Test
    void testAddToCartPlus() {
        Mockito.when(orderService.addPosition( 2L))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/orders/cart/2")
                .bodyValue("action=" + ActionType.PLUS.getName())
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/");
    }

    @Test
    void testAddToCartMinus() {
        Mockito.when(orderService.removePosition(2L))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/orders/cart/2")
                .bodyValue("action=" + ActionType.MINUS.getName())
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/");
    }
}



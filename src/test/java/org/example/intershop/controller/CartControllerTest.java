package org.example.intershop.controller;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(PaymentController.class)
public class CartControllerTest {


    @Mock
    private CartService cartService;

    @MockitoBean
    PositionRepo positionRepo;
    @MockitoBean
    OrderRepo orderRepo;
    @MockitoBean
    ItemRepo itemRepo;

    @Mock
    private Model model;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCartPage() {
        OrderDto dto = new OrderDto();
        dto.setItems(List.of(new ItemDto(1L, "Item1", "desc", "img", 100, 2L, 0L)));
        when(cartService.getBin()).thenReturn(dto);


        String viewName = paymentController.getCartPage(model);

        assertEquals("cart", viewName);
        verify(model).addAttribute(eq("items"), any());
        verify(model).addAttribute(eq("total"), eq(200L)); // 100*2
    }

    @Test
    void testPay() {
        when(cartService.pay()).thenReturn(42L);

        String redirect = paymentController.pay(model);

        assertEquals("redirect:/orders/42", redirect);
    }
}

package org.example.intershop.controller;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.service.CartService;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class PaymentController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getCartPage(Model model) {

         OrderDto dto = cartService.getBin();

        Long total = dto.getItems().stream()
                .mapToLong(item -> item.getPrice() * item.getCount())
                .sum();

        model.addAttribute("items", dto.getItems());
        model.addAttribute("total", total);


        return "cart";
    }
}

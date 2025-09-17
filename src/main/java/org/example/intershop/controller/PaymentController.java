package org.example.intershop.controller;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.service.CartService;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class PaymentController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getCartPage() {

//        List<ItemDto> items =null;//= cartService.getCart();
//        Long total = items.stream()
//                .mapToLong(item -> item.getPrice() * item.getCount())
//                .sum();
//
//        model.addAttribute("items", items);
//        model.addAttribute("total", total);
//        model.addAttribute("empty", "");

        return "cart";
    }
}

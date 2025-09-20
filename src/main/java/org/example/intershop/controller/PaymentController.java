package org.example.intershop.controller;

import org.example.intershop.DTO.OrderDto;
import org.example.intershop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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



    @PostMapping("/pay")
    public String pay(
            Model model
    ) {

       Long id=cartService.pay();

        return "redirect:/orders/"+id;
    }
}

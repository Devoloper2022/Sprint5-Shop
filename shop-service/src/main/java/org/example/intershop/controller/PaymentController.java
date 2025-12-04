package org.example.intershop.controller;


import org.example.intershop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/cart")
public class PaymentController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public Mono<String> getCartPage(Model model) {
        return cartService.getBin()
                .map(dto -> {
                    Long total = dto.getItems().stream()
                            .mapToLong(item -> item.getPrice() * item.getCount())
                            .sum();

                    model.addAttribute("items", dto.getItems());
                    model.addAttribute("total", total);

                    return "cart";
                });
    }


    @PostMapping("/pay")
    public Mono<String> pay(Model model) {
        return cartService.pay()
                .map(id -> "redirect:/orders/" + id);
    }
}

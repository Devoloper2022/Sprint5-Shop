package org.example.intershop.controller;


import org.example.intershop.DTO.OrderDto;
import org.example.intershop.client.api.PaymentApi;
import org.example.intershop.client.model.BalanceResponse;
import org.example.intershop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Controller
@RequestMapping("/cart")
public class PaymentController {

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentApi paymentApi;

    @GetMapping
    public Mono<String> getCartPage(Model model) {


        Mono<BigDecimal> balanceMono = paymentApi.getBalance()
                .map(BalanceResponse::getBalance)
                .onErrorResume(error -> {
                    System.out.println("Ошибка при обращении в платежный сервис: {}" + error.getMessage() + error);
                    return Mono.just(BigDecimal.ONE.negate());
                });

        Mono<OrderDto> cartMono = cartService.getBin();



        return Mono.zip(balanceMono, cartMono)
                .map(tuple -> {
                    BigDecimal balance = tuple.getT1();
                    OrderDto dto = tuple.getT2();

                    long total = dto.getItems().stream()
                            .mapToLong(item -> item.getPrice() * item.getCount())
                            .sum();

                    model.addAttribute("balance", balance);
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

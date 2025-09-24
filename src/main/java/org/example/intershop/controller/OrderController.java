package org.example.intershop.controller;


import org.example.intershop.DTO.ActionType;
import org.example.intershop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public Mono<String> getOrdersPage(Model model) {
        return orderService.findOrders()
                .map(orderHistoryDto -> {
                    model.addAttribute("orders", orderHistoryDto);
                    return "orders";
                });
    }

    @GetMapping("/{id}")
    public Mono<String> getOrderPage(@PathVariable Long id, Model model) {
        return orderService.findOrderById(id)
                .map(orderDto -> {
                    model.addAttribute("order", orderDto);
                    return "order";
                });
    }

    @PostMapping("/action/{id}")
    public Mono<String> countItem(
            @PathVariable("id") Long itemId,
            @RequestParam String action
    ) {
        Mono<Void> actionMono;

        if (ActionType.PLUS.getName().equals(action)) {
            actionMono = orderService.incrementPosition(itemId);
        } else if (ActionType.MINUS.getName().equals(action)) {
            actionMono = orderService.decrementPosition(itemId);
        } else {
            actionMono = Mono.empty();
        }

        return actionMono.thenReturn("redirect:/");
    }


    @PostMapping("/cart/{id}")
    public Mono<String> addToCart(
            @PathVariable("id") Long id,
            @RequestParam String action
    ) {
        Mono<Void> actionMono;

        if (ActionType.PLUS.getName().equals(action)) {
            actionMono = orderService.addPosition(1L, id);
        } else if (ActionType.MINUS.getName().equals(action)) {
            actionMono = orderService.removePosition(id);
        } else {
            actionMono = Mono.empty();
        }

        return actionMono.thenReturn("redirect:/");
    }


}

package org.example.intershop.controller;


import org.example.intershop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getCartPage() {

//        List<ItemDto> items =null;//= cartService.getCart();
//        Long total = items.stream()
//                .mapToLong(item -> item.getPrice() * item.getCount())
//                .sum();
//
//        model.addAttribute("items", items);
//        model.addAttribute("total", total);
//        model.addAttribute("empty", items.isEmpty());

        return "orders";
    }

}

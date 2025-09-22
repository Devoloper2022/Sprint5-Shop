package org.example.intershop.controller;


import org.example.intershop.DTO.ActionType;
import org.example.intershop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getOrdersPage(Model model) {
        model.addAttribute("orders", orderService.findOrders());

        return "orders";
    }


    @GetMapping("/{id}")
    public String getOrderPage(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findOrderById(id));

        return "order";
    }

    @PostMapping("/action/{id}")
    public String countItem(
            @PathVariable("id") Long itemId,
            @RequestParam String action
    ) {
        if (action.equals(ActionType.PLUS.getName())) {
            orderService.incrementPosition(itemId);
        }
        else if (action.equals(ActionType.MINUS.getName())) {
            orderService.decrementPosition(itemId);
        }


        return "redirect:/";
    }

    @PostMapping("/cart/{id}")
    public String addToCart(
            @PathVariable("id") Long id,
            @RequestParam String action
    ) {
        if (action.equals(ActionType.PLUS.getName())) {
            orderService.addPosition(1L, id);
        }
        else if (action.equals(ActionType.MINUS.getName())) {
            orderService.removePosition(id);
        }


        return "redirect:/";
    }

}

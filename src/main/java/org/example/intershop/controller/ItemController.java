package org.example.intershop.controller;

import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping("/{id}")
    public Mono<String> getItemById(
            @PathVariable("id") Long id,
            Model model) {

        return itemService.getItemById(id)
                .doOnNext(item -> {
                    System.out.println(item);
                    model.addAttribute("item", item);
                })
                .thenReturn("item");
    }
}

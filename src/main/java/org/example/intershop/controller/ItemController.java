package org.example.intershop.controller;


import org.example.intershop.DTO.ItemDto;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping("/{id}")
    public String getItemById(
            @PathVariable("id") Long id,
            Model model) {

        ItemDto item = itemService.getItemById(id);
        model.addAttribute("item", item);

        return "item";
    }

    @GetMapping("/add")
    public String getAddPage() {
        return "addItem";
    }

    @PostMapping
    public String addItem(
            @ModelAttribute ItemDto dto
    ) {
        Long id = itemService.addItem(dto);

        return "redirect:/" + id;
    }


    @GetMapping("/{id}/edit")
    public String getEditPage(
            @PathVariable("id") Long id,
            Model model
    ) {
        ItemDto item = itemService.getItemById(id);
        model.addAttribute("item", item);

        return "addItem";
    }


    @PostMapping("{id}/edit")
    public String editItem(
            @PathVariable("id") Long id,
            @ModelAttribute ItemDto dto
    ) {

        itemService.editItem(dto, id);

        return "redirect:/" + id;
    }

    @PostMapping(value = "/{id}/delete")
    public String deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);

        return "main";
    }


    @PostMapping("{id}")
    public String countItem(
            @PathVariable("id") Long id,
            @RequestParam String action
    ) {
/// ?????
        return "redirect:/" + id;
    }


}

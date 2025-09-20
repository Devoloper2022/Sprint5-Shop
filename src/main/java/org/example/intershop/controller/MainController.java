package org.example.intershop.controller;


import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.MainDTO;
import org.example.intershop.DTO.SortType;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ItemService itemService;

    @GetMapping()
    public String getMainPage(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "NO") SortType sort,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            Model model
    ) {

        Page<ItemDto> items = itemService.findAllItemsPagingAndSorting(search, sort, pageSize, pageNumber);
        model.addAttribute("items", items.getContent());
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("paging", new MainDTO(items.getNumber(), items.getSize(), items.getContent().size()));

        return "main";
    }
}

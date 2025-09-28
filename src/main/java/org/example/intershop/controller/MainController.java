package org.example.intershop.controller;


import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.MainDTO;
import org.example.intershop.DTO.SortType;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ItemService itemService;

    @GetMapping()
    public Mono<String> showItems(@RequestParam(required = false) String search,
                                  @RequestParam(defaultValue = "NO") SortType sort,
                                  @RequestParam(defaultValue = "1") Integer pageNumber,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  Model model) {

        Flux<ItemDto> items = itemService.findAllItemsPagingAndSorting(search, sort, pageSize, pageNumber);

        return items.collectList()
                .flatMap(list -> {
                    model.addAttribute("items", list);
                    model.addAttribute("search", search);
                    model.addAttribute("sort", sort);
                    return items.count();
                })
                .map(total -> {
                    model.addAttribute("paging", new MainDTO(pageNumber, pageSize, Math.toIntExact(total)));
                    return "main";
                });
    }
}


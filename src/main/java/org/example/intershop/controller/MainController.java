package org.example.intershop.controller;


import org.example.intershop.DTO.MainDTO;
import org.example.intershop.DTO.SortType;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public Mono<String>  getMainPage(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "NO") SortType sort,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNumber,
            Model model
    ) {
        return itemService.findAllItemsPagingAndSorting(search, sort, pageSize, pageNumber)
                .collectList()
                .doOnNext(items -> {
                    model.addAttribute("items", items);
                    model.addAttribute("search", search);
                    model.addAttribute("sort", sort);
                    model.addAttribute("paging", new MainDTO(pageNumber, pageSize, items.size()));
                })
                .thenReturn("main");
    }


//    @GetMapping()
//    @ResponseBody
//    public Mono<String> getMainPage() {
//        return Mono.just("Hello from WebFlux REST!");
//    }
}


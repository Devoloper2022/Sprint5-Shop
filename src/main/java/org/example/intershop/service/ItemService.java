package org.example.intershop.service;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.SortType;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ItemService {
    Mono<ItemDto> getItemById(Long itemId);

    Flux<ItemDto> findAllItemsPagingAndSorting(String search, SortType sort, Integer pageSize, Integer pageNumber);


    Mono<Void> addItem(ItemDto itemDto);

    Mono<Void> editItem(ItemDto itemDto, Long id);

    Mono<Void> deleteItem(Long itemId);

}

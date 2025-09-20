package org.example.intershop.service;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.SortType;
import org.springframework.data.domain.Page;


public interface ItemService {
    ItemDto getItemById(Long itemId);

    Page<ItemDto> findAllItemsPagingAndSorting(String search, SortType sort, Integer pageSize, Integer pageNumber);


    Long addItem(ItemDto itemDto);

    void editItem(ItemDto itemDto, Long id);

    void deleteItem(Long itemId);

}

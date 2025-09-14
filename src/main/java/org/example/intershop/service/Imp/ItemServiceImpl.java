package org.example.intershop.service.Imp;


import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.SortType;
import org.example.intershop.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public ItemDto getItemById(Long id
    ) {
        return null;
    }

    @Override
    public List<ItemDto> findAllItemsPagingAndSorting(String search, SortType sort, Integer pageSize, Integer pageNumber) {
        return List.of();
    }


    @Override
    public Long addItem(ItemDto itemDto) {
        return 0L;
    }

    @Override
    public void editItem(ItemDto itemDto, Long id) {

    }

    @Override
    public void deleteItem(Long id
    ) {

    }


}

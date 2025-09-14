package org.example.intershop.service.Imp;


import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.MainDTO;
import org.example.intershop.DTO.SortType;
import org.example.intershop.models.entity.Item;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo repo;

    @Override
    public ItemDto getItemById(Long id
    ) {
        return null;
    }

    @Override
    public Page<ItemDto> findAllItemsPagingAndSorting(String search, SortType sort, Integer pageSize, Integer pageNumber) {
        Pageable page = new MainDTO(pageSize, pageNumber - 1).getPageable(sort);
        Page<Item> items = repo.searchAllPagingAndSorting(search, page);

        return items.map(item -> new ItemDto(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getImgName(),
                0,
                item.getPrice()
        ));
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

package org.example.intershop.service.Imp;


import lombok.RequiredArgsConstructor;
import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.MainDTO;
import org.example.intershop.DTO.SortType;
import org.example.intershop.models.entity.Item;
import org.example.intershop.models.entity.Position;
import org.example.intershop.models.mapper.ItemMapper;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo repo;

    private final ItemMapper itemMapper;
    @Autowired
    private PositionRepo positionRepo;


    @Override
    public ItemDto getItemById(Long id
    ) {
        Item item = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Position position = null;
        if (positionRepo.findById(id).isPresent()) {
            position = positionRepo.findById(id).get();
        }
        ItemDto itemDto = itemMapper.toItemDto(item);
        itemDto.setCount(  position != null ? position.getQuantity() : 0);
        itemDto.setPositionID(position != null ? position.getId() : null);
        return itemDto;

    }

    @Override
    public Page<ItemDto> findAllItemsPagingAndSorting(String search, SortType sort, Integer pageSize, Integer pageNumber) {
        Pageable page = new MainDTO(pageSize, pageNumber - 1).getPageable(sort);
        Page<Item> items = repo.searchAllPagingAndSorting(search, page);



        List<ItemDto> dtoList = items.stream()
                .map(item -> {
                    Position position = null;
                    int total = 0;
                    if (positionRepo.existsByItemIdAndStatusFalse(item.getId())) {
                        position = positionRepo.findByItemIdAndStatusFalse(item.getId()).orElse(null);
                        if (position != null) {
                            total = position.getQuantity();
                        }
                    }

                    return new ItemDto(
                            item.getId(),
                            item.getTitle(),
                            item.getDescription(),
                            item.getImgname(),
                            total,
                            item.getPrice(),
                            position != null ? position.getId() : 0L
                    );
                })
                .toList();  // ✅ Collect into List

        return new PageImpl<>(dtoList, page, items.getTotalElements());
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

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Autowired
    private final ItemRepo repo;

    @Autowired
    private final ItemMapper itemMapper;
    @Autowired
    private final PositionRepo positionRepo;


    @Override
    public Mono<ItemDto> getItemById(Long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Item not found")))
                .flatMap(item ->
                        positionRepo.findById(id)
                                .defaultIfEmpty(new Position())
                                .map(position -> {
                                    ItemDto itemDto = itemMapper.toItemDto(item);
                                    if (position.getId() != null) {
                                        itemDto.setCount(position.getQuantity());
                                        itemDto.setPositionID(position.getId());
                                    }
                                    else {
                                        itemDto.setCount(0);
                                        itemDto.setPositionID(null);
                                    }
                                    return itemDto;
                                })
                );
    }


    @Override
    public Flux<ItemDto> findAllItemsPagingAndSorting(String search, SortType sort, Integer pageSize, Integer pageNumber) {
        int offset = Math.max(0, (pageNumber - 1) * pageSize);

        return repo.searchAll(search, pageSize, offset, sort.fromValue(sort))
                .flatMap(item ->
                        positionRepo.findByItemIdAndStatusFalse(item.getId()) // Mono<Position>
                                .defaultIfEmpty(new Position()) // если позиции нет
                                .map(position -> new ItemDto(
                                        item.getId(),
                                        item.getTitle(),
                                        item.getDescription(),
                                        item.getImgname(),
                                        position.getId() != null ? position.getQuantity() : 0,
                                        item.getPrice(),
                                        position.getId() != null ? position.getId() : 0L
                                ))
                );
    }

    @Override
    public Mono<Void> addItem(ItemDto itemDto) {
        return null;
    }

    @Override
    public Mono<Void> editItem(ItemDto itemDto, Long id) {
        return null;
    }

    @Override
    public Mono<Void> deleteItem(Long itemId) {
        return null;
    }


}

package org.example.intershop.service.Imp;


import lombok.RequiredArgsConstructor;
import org.example.intershop.DTO.ItemDto;

import org.example.intershop.DTO.SortType;
import org.example.intershop.models.entity.Position;
import org.example.intershop.models.mapper.ItemMapper;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        if (search == null || search.isEmpty()) {
            search = "";
        }


        positionRepo.existsByItemIdAndStatusFalse(0L)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just("position found");
                    }
                    else {
                        return Mono.just("position missing");
                    }
                });


        return repo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, sort.getSort(sort))
                .flatMap(item ->
                        positionRepo.findByItemIdAndStatusFalse(item.getId())
                                .map(position -> new ItemDto(
                                        item.getId(),
                                        item.getTitle(),
                                        item.getDescription(),
                                        item.getImgname(),
                                        position.getQuantity(),
                                        item.getPrice(),
                                        position.getId()

                                ))
                                .defaultIfEmpty(new ItemDto(
                                        item.getId(),
                                        item.getTitle(),
                                        item.getDescription(),
                                        item.getImgname(),
                                        0,
                                        item.getPrice(),
                                        0L
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

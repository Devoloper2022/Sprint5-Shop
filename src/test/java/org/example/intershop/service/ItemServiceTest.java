package org.example.intershop.service;

import org.example.intershop.DTO.ItemDto;

import org.example.intershop.DTO.SortType;
import org.example.intershop.models.entity.Item;
import org.example.intershop.models.entity.Position;
import org.example.intershop.models.mapper.ItemMapper;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.Imp.ItemServiceImpl;
import org.springframework.data.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    private ItemRepo itemRepo;

    @Mock
    private PositionRepo positionRepo;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemServiceImpl itemService;


    @Test
    void testGetItemById_whenPositionExists() {
        Item item = new Item(1L, "Title", "Desc", "img.png", 100L);
        Position position = new Position(10L, 1L ,1L ,10, false);
        ItemDto dto = new ItemDto(1L, "Title", "Desc", "img.png", 10, 100L, 10L);

        when(itemRepo.findById(1L)).thenReturn(Mono.just(item));
        when(positionRepo.findById(1L)).thenReturn(Mono.just(position));
        when(itemMapper.toItemDto(item)).thenReturn(dto);

        StepVerifier.create(itemService.getItemById(1L))
                .assertNext(result -> {
                    assertEquals(10, result.getCount());
                    assertEquals(10L, result.getPositionID());
                })
                .verifyComplete();
    }

    @Test
    void testGetItemById_whenItemNotFound() {
        when(itemRepo.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(itemService.getItemById(1L))
                .expectError(RuntimeException.class)
                .verify();
    }


}

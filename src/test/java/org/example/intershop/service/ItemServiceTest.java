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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Optional;

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
    void testGetItemById_withPosition() {
        Item item = new Item(1L, "Phone", "Smartphone", "phone.png", 1000L);
        Position position = new Position(1L, null,item.getId(), 5, false);

        ItemDto mappedDto = new ItemDto();
        mappedDto.setId(1L);
        mappedDto.setTitle("Phone");

        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        when(positionRepo.findById(1L)).thenReturn(Optional.of(position));
        when(itemMapper.toItemDto(item)).thenReturn(mappedDto);

        ItemDto result = itemService.getItemById(1L);

        assertEquals("Phone", result.getTitle());
        assertEquals(5, result.getCount());
        assertEquals(1L, result.getPositionID());
    }

    @Test
    void testGetItemById_withoutPosition() {
        Item item = new Item(2L, "Tablet", "Android", "tablet.png", 500L);

        ItemDto mappedDto = new ItemDto();
        mappedDto.setId(2L);
        mappedDto.setTitle("Tablet");

        when(itemRepo.findById(2L)).thenReturn(Optional.of(item));
        when(positionRepo.findById(2L)).thenReturn(Optional.empty());
        when(itemMapper.toItemDto(item)).thenReturn(mappedDto);

        ItemDto result = itemService.getItemById(2L);

        assertEquals("Tablet", result.getTitle());
        assertEquals(0, result.getCount());
        assertNull(result.getPositionID());
    }

    @Test
    void testFindAllItemsPagingAndSorting_withPosition() {
        // given
        Item item = new Item(1L, "Phone", "Smartphone", "phone.png", 1000L);
        Position position = new Position(10L,null, item.getId(), 5, false);

        Page<Item> page = new PageImpl<>(List.of(item), PageRequest.of(0, 10), 1);

        when(itemRepo.searchAllPagingAndSorting(eq("phone"), any(Pageable.class)))
                .thenReturn(page);
        when(positionRepo.existsByItemIdAndStatusFalse(1L)).thenReturn(true);
        when(positionRepo.findByItemIdAndStatusFalse(1L)).thenReturn(Optional.of(position));

        // when
        Page<ItemDto> result = itemService.findAllItemsPagingAndSorting("phone", SortType.NO, 10, 1);

        // then
        assertEquals(1, result.getTotalElements());
        ItemDto dto = result.getContent().get(0);
        assertEquals(1L, dto.getId());
        assertEquals("Phone", dto.getTitle());
        assertEquals(5, dto.getCount());
        assertEquals(10L, dto.getPositionID());

        verify(itemRepo, times(1)).searchAllPagingAndSorting(eq("phone"), any(Pageable.class));
        verify(positionRepo, times(1)).existsByItemIdAndStatusFalse(1L);
    }
}

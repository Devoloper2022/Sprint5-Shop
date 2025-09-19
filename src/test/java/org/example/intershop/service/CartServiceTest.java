package org.example.intershop.service;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.models.entity.OrderEntity;
import org.example.intershop.models.entity.Position;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.Imp.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)

public class CartServiceTest {

    @Mock
    private PositionRepo positionRepo;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void pay_createsNewOrderAndTransfersPositions() {
        // given
        OrderEntity oldOrder = new OrderEntity();
        oldOrder.setId(1L);
        oldOrder.setStatus(false);

        OrderEntity newOrder = new OrderEntity();
        newOrder.setId(2L);
        newOrder.setStatus(true);

        when(orderRepo.findByIdAndStatusFalse(1L)).thenReturn(Optional.of(oldOrder));
        when(orderRepo.save(any(OrderEntity.class))).thenReturn(newOrder);

        Position pos1 = new Position();
        pos1.setId(10L);
        pos1.setOrderId(1L);
        pos1.setStatus(false);

        Position pos2 = new Position();
        pos2.setId(11L);
        pos2.setOrderId(1L);
        pos2.setStatus(false);

        when(positionRepo.findAllByOrderId(1L)).thenReturn(List.of(pos1, pos2));

        Long resultOrderId = cartService.pay();

        assertThat(resultOrderId).isEqualTo(2L);

        verify(orderRepo).save(any(OrderEntity.class));
        verify(positionRepo, times(2)).save(any(Position.class));

        assertThat(pos1.getOrderId()).isEqualTo(2L);
        assertThat(pos1.isStatus()).isTrue();
        assertThat(pos2.getOrderId()).isEqualTo(2L);
        assertThat(pos2.isStatus()).isTrue();
    }

    @Test
    void getBin_returnsOrderDtoWithItems() {

        Position pos = new Position();
        pos.setId(10L);
        pos.setItemId(100L);
        pos.setQuantity(3);
        pos.setStatus(false);

        when(positionRepo.findAllByStatusFalse()).thenReturn(List.of(pos));

        ItemDto item = new ItemDto();
        item.setId(100L);
        item.setTitle("Pizza");
        item.setPrice(150L);

        when(itemService.getItemById(100L)).thenReturn(item);

        OrderDto dto = cartService.getBin();

        assertThat(dto.getItems()).hasSize(1);
        assertThat(dto.getItems().get(0).getId()).isEqualTo(100L);
        assertThat(dto.getItems().get(0).getCount()).isEqualTo(3);
        assertThat(dto.getItems().get(0).getPositionID()).isEqualTo(10L);
    }
}

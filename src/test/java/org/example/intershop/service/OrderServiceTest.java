package org.example.intershop.service;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.DTO.OrderHistoryDto;
import org.example.intershop.models.entity.OrderEntity;
import org.example.intershop.models.entity.Position;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;

import org.example.intershop.service.Imp.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {


    @Mock
    private OrderRepo orderRepo;

    @Mock
    private PositionRepo positionRepo;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findOrders_returnsHistory() {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatus(true);

        when(orderRepo.findAllByStatusTrue())
                .thenReturn(Collections.singletonList(orderEntity));


        OrderService spyService = spy(orderService);
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setTotalSum(200);
        doReturn(orderDto).when(spyService).findOrderById(1L);


        OrderHistoryDto historyDto = spyService.findOrders();


        assertThat(historyDto.getCount()).isEqualTo(1);
        assertThat(historyDto.getPrice()).isEqualTo(200);
        assertThat(historyDto.getList()).hasSize(1);
    }


    @Test
    void findOrderById_buildsOrderDtoCorrectly() {

        Long orderId = 1L;


        Position position = new Position();
        position.setId(10L);
        position.setItemId(100L);
        position.setQuantity(2);

        when(positionRepo.findAllByOrderId(orderId))
                .thenReturn(List.of(position));


        ItemDto item = new ItemDto();
        item.setId(100L);
        item.setPrice(150L);
        item.setTitle("Test item");

        when(itemService.getItemById(100L)).thenReturn(item);


        OrderDto dto = orderService.findOrderById(orderId);


        assertThat(dto.getId()).isEqualTo(orderId);
        assertThat(dto.getItems()).hasSize(1);
        assertThat(dto.getQuantity()).isEqualTo(2);
        assertThat(dto.getTotalSum()).isEqualTo(300);
        assertThat(dto.getItems().get(0).getPositionID()).isEqualTo(10L);
    }



    @Test
    void addPosition_createsNewOrderAndPosition_whenOrderDoesNotExist() {

        Long orderId = 1L;
        Long itemId = 100L;

        when(orderRepo.existsByIdAndStatusFalse(orderId)).thenReturn(false);

        OrderEntity savedOrder = new OrderEntity();
        savedOrder.setId(1L);
        savedOrder.setStatus(false);
        when(orderRepo.save(any(OrderEntity.class))).thenReturn(savedOrder);

        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(false);

        Position savedPosition = new Position();
        savedPosition.setId(10L);
        when(positionRepo.save(any(Position.class))).thenReturn(savedPosition);


        orderService.addPosition(orderId, itemId);


        verify(orderRepo).save(any(OrderEntity.class));
        verify(positionRepo, times(2)).save(any(Position.class));
    }


    @Test
    void removePosition_deletesPositionById() {
        Long positionId = 50L;

        orderService.removePosition(positionId);
        verify(positionRepo).deleteById(positionId);
    }



    @Test
    void incrementPosition_createsNewPosition_whenNotExists() {
        Long itemId = 100L;

        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(false);

        Position saved = new Position();
        saved.setId(1L);
        saved.setQuantity(0);
        when(positionRepo.save(any(Position.class))).thenReturn(saved);

        orderService.incrementPosition(itemId);

        verify(positionRepo, times(2)).save(any(Position.class));
    }

    @Test
    void incrementPosition_increasesQuantity_whenExists() {
        Long itemId = 100L;
        Position existing = new Position();
        existing.setId(10L);
        existing.setItemId(itemId);
        existing.setQuantity(5);

        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(true);
        when(positionRepo.findByItemIdAndStatusFalse(itemId)).thenReturn(Optional.of(existing));


        orderService.incrementPosition(itemId);


        verify(positionRepo).save(existing);
        assert(existing.getQuantity() == 6);
    }

    @Test
    void decrementPosition_doesNothing_whenPositionDoesNotExist() {
        Long itemId = 100L;

        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(false);

        orderService.decrementPosition(itemId);

        verify(positionRepo, never()).save(any(Position.class));
        verify(positionRepo, never()).deleteById(anyLong());
    }

    @Test
    void decrementPosition_decreasesQuantity_whenAboveZero() {
        Long itemId = 100L;
        Position existing = new Position();
        existing.setId(10L);
        existing.setItemId(itemId);
        existing.setQuantity(3);

        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(true);
        when(positionRepo.findByItemIdAndStatusFalse(itemId)).thenReturn(Optional.of(existing));

        orderService.decrementPosition(itemId);

        verify(positionRepo).save(existing);
        assert(existing.getQuantity() == 2);
    }

    @Test
    void decrementPosition_deletesPosition_whenQuantityBecomesZero() {
        Long itemId = 100L;
        Position existing = new Position();
        existing.setId(10L);
        existing.setItemId(itemId);
        existing.setQuantity(1);

        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(true);
        when(positionRepo.findByItemIdAndStatusFalse(itemId)).thenReturn(Optional.of(existing));

        orderService.decrementPosition(itemId);

        verify(positionRepo).deleteById(existing.getId());
        verify(positionRepo, never()).save(existing);
    }

}

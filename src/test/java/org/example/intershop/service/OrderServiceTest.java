package org.example.intershop.service;

import org.example.intershop.DTO.ItemDto;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testFindOrderById_withItems() {
        Long orderId = 1L;

        Position position = new Position();
        position.setId(10L);
        position.setItemId(100L);
        position.setQuantity(2);
        position.setOrderId(orderId);

        ItemDto itemDto = new ItemDto();
        itemDto.setId(100L);
        itemDto.setTitle("Test Item");
        itemDto.setPrice(50L);

        when(positionRepo.findAllByOrderId(orderId))
                .thenReturn(Flux.just(position));

        when(itemService.getItemById(100L))
                .thenReturn(Mono.just(itemDto));

        StepVerifier.create(orderService.findOrderById(orderId))
                .assertNext(orderDto -> {
                    assertEquals(orderId, orderDto.getId());
                    assertEquals(1, orderDto.getItems().size());
                    assertEquals(100L, orderDto.getItems().get(0).getId());
                    assertEquals(2, orderDto.getQuantity());
                    assertEquals(100, orderDto.getTotalSum()); // 2 * 50
                })
                .verifyComplete();
    }

    @Test
    void testAddPosition_whenOrderDoesNotExist_createsNewOrderAndPosition() {
        Long orderId = 1L;
        Long itemId = 100L;

        OrderEntity savedOrder = new OrderEntity();
        savedOrder.setId(orderId);
        savedOrder.setStatus(false);

        Position newPosition = new Position();
        newPosition.setId(10L);
        newPosition.setItemId(itemId);
        newPosition.setOrderId(orderId);
        newPosition.setQuantity(1);

        when(orderRepo.existsByIdAndStatusFalse(orderId)).thenReturn(Mono.just(false));
        when(orderRepo.save(any(OrderEntity.class))).thenReturn(Mono.just(savedOrder));


        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(Mono.just(false));
        when(positionRepo.save(any(Position.class))).thenReturn(Mono.just(newPosition));

        StepVerifier.create(orderService.addPosition(orderId, itemId))
                .verifyComplete();

        verify(orderRepo).save(any(OrderEntity.class));
        verify(positionRepo).save(any(Position.class));
    }

    @Test
    void testAddPosition_whenOrderExists_addsPosition() {
        Long orderId = 2L;
        Long itemId = 200L;

        OrderEntity existingOrder = new OrderEntity();
        existingOrder.setId(orderId);
        existingOrder.setStatus(false);

        Position existingPosition = new Position();
        existingPosition.setId(20L);
        existingPosition.setItemId(itemId);
        existingPosition.setOrderId(orderId);
        existingPosition.setQuantity(1);


        when(orderRepo.existsByIdAndStatusFalse(orderId)).thenReturn(Mono.just(true));
        when(orderRepo.findById(orderId)).thenReturn(Mono.just(existingOrder));


        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(Mono.just(false));
        when(positionRepo.save(any(Position.class))).thenReturn(Mono.just(existingPosition));

        StepVerifier.create(orderService.addPosition(orderId, itemId))
                .verifyComplete();

        verify(orderRepo).findById(orderId);
        verify(positionRepo).save(any(Position.class));
    }

    @Test
    void testIncrementPosition_whenPositionDoesNotExist_createsNew() {
        Long itemId = 100L;
        Position newPosition = new Position();
        newPosition.setId(1L);
        newPosition.setItemId(itemId);
        newPosition.setQuantity(1);

        when(positionRepo.existsByItemIdAndStatusFalse(itemId)).thenReturn(Mono.just(false));
        when(positionRepo.save(any(Position.class))).thenReturn(Mono.just(newPosition));

        StepVerifier.create(orderService.incrementPosition(itemId))
                .verifyComplete();

        verify(positionRepo).save(any(Position.class));
    }

    @Test
    void testDecrementPosition_whenQuantityGreaterThanOne_decrements() {
        Long itemId = 300L;
        Position existing = new Position();
        existing.setId(3L);
        existing.setItemId(itemId);
        existing.setQuantity(5);

        Position updated = new Position();
        updated.setId(3L);
        updated.setItemId(itemId);
        updated.setQuantity(4);

        when(positionRepo.findByItemIdAndStatusFalse(itemId)).thenReturn(Mono.just(existing));
        when(positionRepo.save(existing)).thenReturn(Mono.just(updated));

        StepVerifier.create(orderService.decrementPosition(itemId))
                .verifyComplete();

        assertEquals(4, existing.getQuantity());
        verify(positionRepo).save(existing);
    }


}

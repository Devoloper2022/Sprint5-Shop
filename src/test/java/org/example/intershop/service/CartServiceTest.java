package org.example.intershop.service;

import org.example.intershop.DTO.ItemDto;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;


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
        void testPay_success() {
            OrderEntity oldOrder = new OrderEntity();
            oldOrder.setId(1L);
            oldOrder.setStatus(false);

            OrderEntity newOrder = new OrderEntity();
            newOrder.setId(2L);
            newOrder.setStatus(true);

            Position pos = new Position();
            pos.setId(10L);
            pos.setOrderId(oldOrder.getId());
            pos.setItemId(100L);
            pos.setQuantity(2);
            pos.setStatus(false);

            when(orderRepo.findByIdAndStatusFalse(1L)).thenReturn(Mono.just(oldOrder));
            when(orderRepo.save(any(OrderEntity.class))).thenReturn(Mono.just(newOrder));
            when(positionRepo.findAllByOrderId(oldOrder.getId())).thenReturn(Flux.just(pos));
            when(positionRepo.save(any(Position.class))).thenReturn(Mono.just(pos));

            StepVerifier.create(cartService.pay())
                    .expectNext(2L)
                    .verifyComplete();

            verify(orderRepo).findByIdAndStatusFalse(1L);
            verify(orderRepo).save(any(OrderEntity.class));
            verify(positionRepo).findAllByOrderId(1L);
            verify(positionRepo).save(any(Position.class));
        }

        @Test
        void testPay_orderNotFound() {
            when(orderRepo.findByIdAndStatusFalse(1L)).thenReturn(Mono.empty());

            StepVerifier.create(cartService.pay())
                    .expectErrorMatches(err -> err instanceof IllegalStateException &&
                            err.getMessage().equals("Order not found"))
                    .verify();

            verify(orderRepo).findByIdAndStatusFalse(1L);
            verify(orderRepo, never()).save(any());
        }


        @Test
        void testGetBin_success() {

            Position pos1 = new Position();
            pos1.setId(1L);
            pos1.setItemId(101L);
            pos1.setQuantity(2);

            Position pos2 = new Position();
            pos2.setId(2L);
            pos2.setItemId(102L);
            pos2.setQuantity(5);

            when(positionRepo.findAllByStatusFalse()).thenReturn(Flux.just(pos1, pos2));


            ItemDto dto1 = new ItemDto(101L, "item1", "desc1", "img1", 0, null, 0L);
            ItemDto dto2 = new ItemDto(102L, "item2", "desc2", "img2", 0, null, 0L);

            when(itemService.getItemById(101L)).thenReturn(Mono.just(dto1));
            when(itemService.getItemById(102L)).thenReturn(Mono.just(dto2));

            StepVerifier.create(cartService.getBin())
                    .assertNext(orderDto -> {
                        List<ItemDto> items = orderDto.getItems();

                        assert items.size() == 2;

                        ItemDto i1 = items.get(0);
                        assert i1.getId().equals(101L);
                        assert i1.getCount() == 2;
                        assert i1.getPositionID().equals(1L);

                        ItemDto i2 = items.get(1);
                        assert i2.getId().equals(102L);
                        assert i2.getCount() == 5;
                        assert i2.getPositionID().equals(2L);
                    })
                    .verifyComplete();

            verify(positionRepo).findAllByStatusFalse();
            verify(itemService).getItemById(101L);
            verify(itemService).getItemById(102L);
        }

        @Test
        void testGetBin_empty() {
            when(positionRepo.findAllByStatusFalse()).thenReturn(Flux.empty());

            StepVerifier.create(cartService.getBin())
                    .assertNext(orderDto -> {
                        assert orderDto.getItems().isEmpty();
                    })
                    .verifyComplete();

            verify(positionRepo).findAllByStatusFalse();
            verifyNoInteractions(itemService);
        }
}

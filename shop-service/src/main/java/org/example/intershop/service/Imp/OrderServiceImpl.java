package org.example.intershop.service.Imp;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.DTO.OrderHistoryDto;

import org.example.intershop.models.entity.Order;
import org.example.intershop.models.entity.Position;

import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.ItemService;
import org.example.intershop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private PositionRepo positionRepo;
    @Autowired
    private OrderRepo repo;

    @Autowired
    private ItemService itemService;


    @Override
    @Cacheable(value = "allOrders")
    public Mono<OrderHistoryDto> findOrders() {
        return repo.findAllByStatusTrue()
                .flatMap(order ->
                        findOrderById(order.getId())
                )
                .collectList()
                .map(orderList -> {
                    OrderHistoryDto dto = new OrderHistoryDto();
                    dto.setList(orderList);
                    dto.setCount(orderList.size());
                    dto.setPrice(
                            (int) orderList.stream()
                                    .mapToDouble(OrderDto::getTotalSum)
                                    .sum()
                    );
                    return dto;
                });
    }

    @Override
    @Cacheable(value = "orders", key = "#orderId", unless = "#result == null")
    public Mono<OrderDto> findOrderById(Long orderId) {
        Flux<ItemDto> itemsFlux = positionRepo.findAllByOrderId(orderId)
                .flatMap(position ->
                        itemService.getItemById(position.getItemId())
                                .map(itemDto -> {
                                    itemDto.setCount(position.getQuantity());
                                    itemDto.setPositionID(position.getId());
                                    return itemDto;
                                })
                );

        return itemsFlux
                .collectList()
                .map(itemDtos -> {
                    OrderDto dto = new OrderDto();
                    dto.setId(orderId);
                    dto.setItems(itemDtos);

                    int totalQuantity = itemDtos.stream()
                            .mapToInt(ItemDto::getCount)
                            .sum();

                    int totalSum = itemDtos.stream()
                            .mapToInt(i -> Math.toIntExact(i.getCount() * i.getPrice()))
                            .sum();

                    dto.setQuantity(totalQuantity);
                    dto.setTotalSum(totalSum);
                    return dto;
                });
    }

    @Override
//    @CacheEvict(value = {"orders", "allOrders"}, allEntries = true)
    public Mono<Void> addPosition(Long orderId, Long itemId) {
        return repo.existsByIdAndStatusFalse(orderId)
                .flatMap(exists -> {
                    Mono<Order> orderMono;
                    if (!exists) {
                        Order newOrder = new Order();
                        newOrder.setStatus(false);
                        orderMono = repo.save(newOrder);
                    }
                    else {
                        orderMono = repo.findById(orderId);
                    }

                    return orderMono.flatMap(orderEntity ->
                            positionRepo.existsByItemIdAndStatusFalse(itemId)
                                    .flatMap(positionExists -> {
                                        Mono<Position> positionMono;
                                        if (!positionExists) {
                                            Position newPosition = new Position();
                                            newPosition.setItemId(itemId);
                                            newPosition.setOrderId(orderEntity.getId());
                                            newPosition.setQuantity(1);
                                            positionMono = positionRepo.save(newPosition);
                                        }
                                        else {
                                            positionMono = positionRepo.findByItemIdAndStatusFalse(itemId)
                                                    .flatMap(existing -> {
                                                        existing.setQuantity(existing.getQuantity());
                                                        existing.setOrderId(orderEntity.getId());
                                                        return positionRepo.save(existing);
                                                    });
                                        }
                                        return positionMono.then();
                                    })
                    );
                })
                .then();
    }

    @Override
//    @CacheEvict(value = {"orders", "allOrders"}, key = "#orderId")
    public Mono<Void> removePosition(Long positionId) {
        return positionRepo.deleteById(positionId);
    }

    @Override
    public Mono<Void> incrementPosition(Long itemId) {
        return positionRepo.existsByItemIdAndStatusFalse(itemId)
                .flatMap(exists -> {
                    if (!exists) {
                        Position newPosition = new Position();
                        newPosition.setItemId(itemId);
                        newPosition.setQuantity(1); // сразу ставим 1
                        return positionRepo.save(newPosition).then();
                    }
                    else {
                        return positionRepo.findByItemIdAndStatusFalse(itemId)
                                .flatMap(existing -> {
                                    existing.setQuantity(existing.getQuantity() + 1);
                                    return positionRepo.save(existing).then();
                                });
                    }
                });
    }

    @Override
    public Mono<Void> decrementPosition(Long itemId) {
        return positionRepo.findByItemIdAndStatusFalse(itemId)
                .flatMap(position -> {
                    if (position.getQuantity() > 1) {
                        position.setQuantity(position.getQuantity() - 1);
                        return positionRepo.save(position).then();
                    }
                    else {

                        return positionRepo.deleteById(position.getId());
                    }
                })
                .then();
    }
}

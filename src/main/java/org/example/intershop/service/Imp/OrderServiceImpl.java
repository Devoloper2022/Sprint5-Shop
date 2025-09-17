package org.example.intershop.service.Imp;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.DTO.OrderHistoryDto;
import org.example.intershop.models.entity.Item;
import org.example.intershop.models.entity.OrderEntity;
import org.example.intershop.models.entity.Position;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.ItemService;
import org.example.intershop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    PositionRepo positionRepo;
    @Autowired
    private OrderRepo repo;

    @Autowired
    private ItemService itemService;


    @Override
    public Long createOrder() {
        return 0L;
    }

    @Override
    public List<OrderHistoryDto> findOrders() {

        return List.of();
    }

    @Override
    public OrderDto findOrderById(Long orderId) {
        OrderDto dto = new OrderDto();
        List<ItemDto> list = new ArrayList<>();

//        OrderEntity entity = repo.findById(orderId).get();
        List<Position> positions = positionRepo.findAllByOrderId(orderId);

        for (Position position : positions) {
            ItemDto itemDto = itemService.getItemById(position.getItemId());
            itemDto.setCount(position.getQuantity());
            itemDto.setPositionID(position.getId());

            list.add(itemDto);
        }

        dto.setItems(list);
        return dto;
    }

    @Override
    public void addPosition(Long ItemId, Long OrderId) {

    }

    @Override
    public void removePosition(Long ItemId, Long OrderId) {

    }
}

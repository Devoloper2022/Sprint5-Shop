package org.example.intershop.service.Imp;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.models.entity.OrderEntity;
import org.example.intershop.models.entity.Position;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.CartService;
import org.example.intershop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    PositionRepo positionRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ItemService itemService;


    @Override
    public Long pay() {
        OrderEntity order = orderRepo.findByIdAndStatusFalse(1l).get();
        OrderEntity newEntity = new OrderEntity();
        newEntity = orderRepo.save(newEntity);

        List<Position> positions = positionRepo.findAllByOrderId(order.getId());
        for (Position position : positions) {
            position.setOrderId(newEntity.getId());
            position.setStatus(true);
            positionRepo.save(position);
        }

        return newEntity.getId();
    }

    @Override
    public OrderDto getBin() {
        OrderDto dto = new OrderDto();
        List<ItemDto> list = new ArrayList<>();
        List<Position> positions = positionRepo.findAllByStatusFalse();

        for (Position position : positions) {
            ItemDto itemDto = itemService.getItemById(position.getItemId());
            itemDto.setCount(position.getQuantity());
            itemDto.setPositionID(position.getId());

            list.add(itemDto);
        }


        dto.setItems(list);
        return dto;
    }


}

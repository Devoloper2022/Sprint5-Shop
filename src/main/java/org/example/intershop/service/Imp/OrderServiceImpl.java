package org.example.intershop.service.Imp;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.DTO.OrderDto;
import org.example.intershop.DTO.OrderHistoryDto;

import org.example.intershop.models.entity.OrderEntity;
import org.example.intershop.models.entity.Position;

import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.ItemService;
import org.example.intershop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    PositionRepo positionRepo;
    @Autowired
    private OrderRepo repo;

    @Autowired
    private ItemService itemService;




    @Override
    public OrderHistoryDto findOrders() {
        OrderHistoryDto dto = new OrderHistoryDto();
        List<OrderEntity> orders = repo.findAllByStatusTrue();
        List<OrderDto> orderList = new ArrayList<>();

        orders.forEach(order -> {
            OrderDto orderDto= findOrderById(order.getId());
            orderList.add(orderDto);
            dto.setPrice(dto.getPrice()+orderDto.getTotalSum());
        });

        dto.setCount(orderList.size());
        dto.setList(orderList);
        return dto;
    }

    @Override
    public OrderDto findOrderById(Long orderId) {
        OrderDto dto = new OrderDto();
        List<ItemDto> list = new ArrayList<>();

        List<Position> positions = positionRepo.findAllByOrderId(orderId);

        for (Position position : positions) {
            ItemDto itemDto = itemService.getItemById(position.getItemId());
            itemDto.setCount(position.getQuantity());
            itemDto.setPositionID(position.getId());
            dto.setQuantity(dto.getQuantity()+itemDto.getCount());
            dto.setTotalSum((int) (dto.getTotalSum()+(itemDto.getCount()*itemDto.getPrice())));
            list.add(itemDto);
        }


        dto.setId(orderId);
        dto.setItems(list);
        return dto;
    }

    @Override
    public void addPosition( Long orderId, Long itemId) {
        OrderEntity entity = new OrderEntity();
        Position position = new Position();
        if (!repo.existsByIdAndStatusFalse(orderId)) {
            entity.setStatus(false);
            entity = repo.save(entity);
        }
        else {
            entity = repo.findById(orderId).get();
        }

        if (!positionRepo.existsByItemIdAndStatusFalse(itemId)) {
            position = positionRepo.save(position);
            position.setQuantity(1);
        }
        else {
            position = positionRepo.findByItemIdAndStatusFalse(itemId).get();
        }

        position.setItemId(itemId);
        position.setOrderId(entity.getId());
        positionRepo.save(position);
    }

    @Override
    public void removePosition(Long positionID) {
        positionRepo.deleteById(positionID);
    }


    @Override
    public void incrementPosition(Long itemId) {
        Position position = new Position();
        System.out.println("Sanat" + positionRepo.existsByItemIdAndStatusFalse(itemId));
        if (!positionRepo.existsByItemIdAndStatusFalse(itemId)) {
            position = positionRepo.save(position);
            position.setQuantity(0);
        }
        else {
            position = positionRepo.findByItemIdAndStatusFalse(itemId).get();
        }

        position.setQuantity(position.getQuantity() + 1);
        position.setItemId(itemId);
        positionRepo.save(position);
    }

    @Override
    public void decrementPosition(Long itemId) {
        Position position = new Position();
        if (positionRepo.existsByItemIdAndStatusFalse(itemId)) {
            position = positionRepo.findByItemIdAndStatusFalse(itemId).get();
        }else {
            return;
        }

        if (position.getQuantity() > 0) {
            position.setQuantity(position.getQuantity() - 1);
        }

        if (position.getQuantity() == 0) {
            positionRepo.deleteById(position.getId());
        }else {
            positionRepo.save(position);
        }

    }


}

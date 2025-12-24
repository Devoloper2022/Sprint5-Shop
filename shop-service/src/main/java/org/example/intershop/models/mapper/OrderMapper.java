package org.example.intershop.models.mapper;

import org.example.intershop.DTO.OrderDto;
import org.example.intershop.models.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(source = "totalPrice", target = "totalSum")
    @Mapping(source = "totalQuantity", target = "quantity")
    @Mapping(target = "items", ignore = true)
    OrderDto toOrderDto(Order order);
}

package org.example.intershop.models.mapper;

import org.example.intershop.DTO.OrderHistoryDto;
import org.example.intershop.models.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(source = "orderId", target = "id")
    @Mapping(source = "count", target = "totalPosition")
    @Mapping(source = "price", target = "totalPrice")
    @Mapping(source = "quantity", target = "totalQuantity")
    OrderHistoryDto toOrderDto(OrderEntity order);
}

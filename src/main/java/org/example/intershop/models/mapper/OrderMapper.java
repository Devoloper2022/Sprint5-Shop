package org.example.intershop.models.mapper;

import org.example.intershop.DTO.OrderHistoryDto;
import org.example.intershop.models.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "totalPosition", target = "count")
    @Mapping(source = "totalPrice", target = "price")
    @Mapping(source = "totalQuantity", target = "quantity")
    OrderHistoryDto toOrderHistoryDto(OrderEntity order);
}

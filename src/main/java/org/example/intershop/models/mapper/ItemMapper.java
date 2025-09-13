package org.example.intershop.models.mapper;


import org.example.intershop.DTO.ItemDto;
import org.example.intershop.models.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    ItemDto toItemDto(Item itemEntity);

    List<ItemDto> toItemDtos(List<Item> itemEntities);
}

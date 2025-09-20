package org.example.intershop.models.mapper;


import org.example.intershop.DTO.ItemDto;
import org.example.intershop.models.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    @Mapping(source = "imgname", target = "imgPath")
    @Mapping(target = "count", ignore = true)
    @Mapping(target = "positionID", ignore = true)
    ItemDto toItemDto(Item itemEntity);

    List<ItemDto> toItemDtos(List<Item> itemEntities);
}

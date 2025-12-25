package org.example.intershop.models.mapper;

import org.example.intershop.DTO.UserDto;
import org.example.intershop.models.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toUserDto(UserEntity user);
}

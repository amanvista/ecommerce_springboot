package com.techstackgo.ecommerce.mapper;

import org.mapstruct.*;

import com.techstackgo.ecommerce.dto.UserDto;
import com.techstackgo.ecommerce.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartial(@MappingTarget User entity, UserDto dto);
}

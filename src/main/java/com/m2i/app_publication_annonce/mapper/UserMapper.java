package com.m2i.app_publication_annonce.mapper;

import com.m2i.app_publication_annonce.controller.dto.CreateUserDto;
import com.m2i.app_publication_annonce.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "username", expression = "java(createUserDto.getUsername() + \" \" + createUserDto.getLastName())")
    UserEntity toEntity(CreateUserDto createUserDto);
}

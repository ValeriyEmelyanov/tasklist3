package com.example.tasklist3.web.mappers;

import com.example.tasklist3.domain.user.User;
import com.example.tasklist3.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);

}

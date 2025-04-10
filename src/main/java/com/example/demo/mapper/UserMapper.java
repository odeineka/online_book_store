package com.example.demo.mapper;

import com.example.demo.config.MapperConfig;
import com.example.demo.dto.user.UserRegistrationRequestDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface UserMapper {
    User toEntity(UserRegistrationRequestDto dto);

    UserResponseDto toDto(User user);
}

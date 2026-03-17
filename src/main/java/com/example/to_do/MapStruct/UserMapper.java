package com.example.to_do.MapStruct;

import com.example.to_do.DTO.RegisterRequestDto;
import com.example.to_do.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convertToEntity(RegisterRequestDto registerRequestDto);
    RegisterRequestDto convertToDto(User user);
}

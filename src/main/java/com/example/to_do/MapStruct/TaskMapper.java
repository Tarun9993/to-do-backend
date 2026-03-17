package com.example.to_do.MapStruct;

import com.example.to_do.DTO.TaskDto;
import com.example.to_do.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task convertToEntity(TaskDto taskDto);
    TaskDto convertToDto(Task task);

}

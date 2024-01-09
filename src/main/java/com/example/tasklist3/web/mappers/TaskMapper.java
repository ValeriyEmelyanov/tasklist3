package com.example.tasklist3.web.mappers;

import com.example.tasklist3.domain.task.Task;
import com.example.tasklist3.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    List<TaskDto> toDto(List<Task> tasks);

    Task toEntity(TaskDto dto);

}

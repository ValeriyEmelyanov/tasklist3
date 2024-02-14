package com.example.tasklist3.web.mappers;

import com.example.tasklist3.domain.task.TaskImage;
import com.example.tasklist3.web.dto.task.TaskImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto> {
}

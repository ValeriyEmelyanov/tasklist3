package com.example.tasklist3.web.controller;

import com.example.tasklist3.domain.task.Task;
import com.example.tasklist3.domain.task.TaskImage;
import com.example.tasklist3.service.TaskService;
import com.example.tasklist3.web.dto.task.TaskDto;
import com.example.tasklist3.web.dto.task.TaskImageDto;
import com.example.tasklist3.web.dto.validation.OnUpdate;
import com.example.tasklist3.web.mappers.TaskImageMapper;
import com.example.tasklist3.web.mappers.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task controller", description = "Task API")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskImageMapper taskImageMapper;

    @PutMapping
    @MutationMapping(name = "updateTask")
    @PreAuthorize("canAccessTask(#dto.id)")
//    @PreAuthorize("@customSecurityExpression.canAccessTask(#dto.id)")
    @Operation(summary = "Update task")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody @Argument final TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }

    @GetMapping("/{id}")
    @QueryMapping(name = "taslById")
    @PreAuthorize("canAccessTask(#id)")
//    @PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
    @Operation(summary = "Get task by id")
    public TaskDto getById(@PathVariable @Argument final Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    @MutationMapping(name = "deleteTask")
    @PreAuthorize("canAccessTask(#id)")
//    @PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
    @Operation(summary = "Delete task by id")
    public void deleteById(@PathVariable @Argument final Long id) {
        taskService.delete(id);
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("canAccessTask(#id)")
    @Operation(summary = "Upload image to task")
    public void uploadImage(@PathVariable final Long id,
                            @Validated @ModelAttribute final TaskImageDto imageDto) {
        TaskImage image = taskImageMapper.toEntity(imageDto);
        taskService.uploadImage(id, image);
    }

}

package com.example.tasklist3.web.controller;

import com.example.tasklist3.domain.task.Task;
import com.example.tasklist3.domain.user.User;
import com.example.tasklist3.service.TaskService;
import com.example.tasklist3.service.UserService;
import com.example.tasklist3.web.dto.task.TaskDto;
import com.example.tasklist3.web.dto.user.UserDto;
import com.example.tasklist3.web.dto.validation.OnCreate;
import com.example.tasklist3.web.dto.validation.OnUpdate;
import com.example.tasklist3.web.mappers.TaskMapper;
import com.example.tasklist3.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping
    @Operation(summary = "Update user")
    public UserDto updated(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updated = userService.update(user);
        return userMapper.toDto(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get all user's tasks")
    public List<TaskDto> getTaskByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{userId}/tasks")
    @Operation(summary = "Add task to user")
    public TaskDto createTask(@PathVariable Long userId, @Validated(OnCreate.class) @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, userId);
        return taskMapper.toDto(createdTask);
    }

}

package com.example.tasklist3.service;

import com.example.tasklist3.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    Task update(Task task);

    Task create(Task task);

    void delete(Long id);

}

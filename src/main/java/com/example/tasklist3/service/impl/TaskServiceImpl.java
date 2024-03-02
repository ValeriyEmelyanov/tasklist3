package com.example.tasklist3.service.impl;

import com.example.tasklist3.domain.exception.ResourceNotFoundException;
import com.example.tasklist3.domain.task.Status;
import com.example.tasklist3.domain.task.Task;
import com.example.tasklist3.domain.task.TaskImage;
import com.example.tasklist3.repository.TaskRepository;
import com.example.tasklist3.service.ImageService;
import com.example.tasklist3.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ImageService imageService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "TaskService::getById", key = "#id")
    public Task getById(final Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(final Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    @CachePut(value = "TaskService::getById", key = "#task.id")
    public Task update(final Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    @Cacheable(
            value = "TaskService::getById",
            condition = "#task.id!=null",
            key = "#task.id"
    )
    public Task create(final Task task, final Long userId) {
        if (task.getStatus() == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.save(task);
        taskRepository.assignTask(userId, task.getId());
        return task;
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void delete(final Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void uploadImage(final Long id, final TaskImage image) {
        String filename = imageService.upload(image);
        taskRepository.addImage(id, filename);
    }
}

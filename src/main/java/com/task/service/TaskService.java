package com.task.service;

import com.task.dto.TaskDto;
import com.task.models.Task;
import com.task.models.UserEntity;
import com.task.respositry.TaskRepository;
import com.task.respositry.UserRepository;
import exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(String username, TaskDto taskDto) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        //UserEntity user = userRepository.findById(task.getCreatedBy().getId()).orElseThrow();
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setDueDate(taskDto.getDueDate());
        task.setPriority(taskDto.getPriority());
        task.setCreatedBy(user);
        return taskRepository.save(task);
    }
    public List<Task> getAllTasks(String username) {
        return taskRepository.findByCreatedByUsername(username);
    }
    public Task getTaskById(String username, Long id) {
        Optional<Task> task =taskRepository.findByIdAndCreatedByUsername(id, username);;
        return task.orElse(null);
    }

    public Task updateTask(String username, Long id, TaskDto taskDto) {
        Task task = taskRepository.findByIdAndCreatedByUsername(id, username).orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setDueDate(taskDto.getDueDate());
        task.setPriority(taskDto.getPriority());
        return taskRepository.save(task);
    }

    public void deleteTask(Task task ) {
        taskRepository.delete(task);
    }
}


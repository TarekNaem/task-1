package com.task.service;

import com.task.models.Task;
import com.task.models.User;
import com.task.respositry.TaskRepository;
import com.task.respositry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> listAllTasks(String username) {
        return taskRepository.findByCreatedByUsername(username);
    }

    public Task createTask(String username, Task task) {
        User user = userRepository.findByUsername(username).orElseThrow();
        task.setCreatedBy(user);
        return taskRepository.save(task);
    }

    public Task updateTask(String username, Long id, Task task) {
        Task ex = taskRepository.findByIdAndCreatedByUsername(id, username).orElseThrow();
        ex.setTitle(task.getTitle());
        ex.setDescription(task.getDescription());
        ex.setStatus(task.getStatus());
        ex.setDueDate(task.getDueDate());
        ex.setPriority(task.getPriority());
        return taskRepository.save(ex);
    }

    public void deleteTask(String username, Long id) {
        Task ex = taskRepository.findByIdAndCreatedByUsername(id, username).orElseThrow();
        taskRepository.delete(ex);
    }
}


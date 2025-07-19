package com.task.controller;

import com.task.models.Task;
import com.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    final private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> list(Authentication auth) {
        return taskService.listAllTasks(auth.getName());
    }

    @PostMapping
    public Task create(Authentication auth, @RequestBody Task t) {
        return taskService.createTask(auth.getName(), t);
    }

    @PutMapping("/{id}")
    public Task update(Authentication auth, @PathVariable Long id, @RequestBody Task t) {
        return taskService.updateTask(auth.getName(), id, t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Authentication auth, @PathVariable Long id) {
        taskService.deleteTask(auth.getName(), id);
        return ResponseEntity.ok().build();
    }
}

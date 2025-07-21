package com.task.controller;

import com.task.models.Task;
import com.task.dto.TaskDto;
import com.task.service.TaskService;
import exception.EntityAlreadyExist;
import exception.EntityBadRequest;
import exception.EntityErorrResponse;
import exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    final private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/addTask")
    public Task createTask(@RequestBody TaskDto taskDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (taskDto.getTitle() == null || taskDto.getTitle().equals("")) {
            throw new EntityBadRequest(" Title is mandatory");
        }else if (taskDto.getStatus() == null || taskDto.getStatus().equals("")) {
            throw new EntityBadRequest(" Status is mandatory");
        }else if (taskDto.getPriority() == null || taskDto.getPriority().equals("")) {
            throw new EntityBadRequest(" Priority is mandatory");
        }

        return taskService.createTask(username, taskDto);
    }

    @GetMapping("/All")
    public List<Task> getAllTasks(Authentication auth) {
        List<Task> tasks = taskService.getAllTasks(auth.getName());
        return tasks;
    }

    @GetMapping("/{id}")
    public Task getTaskById(Authentication auth, @PathVariable Long id) {
        Task task = taskService.getTaskById(auth.getName(), id);
        if(task == null){
            throw new EntityBadRequest(" There is no Task by Id :"+id);
        }
        return task;
    }


    @PutMapping("/{id}")
    public Task updateTask(Authentication auth, @PathVariable Long id, @RequestBody TaskDto taskDto) {
        if (taskDto.getTitle() == null || taskDto.getTitle().equals("")) {
            throw new EntityBadRequest(" Title is mandatory");
        }else if (taskDto.getStatus() == null || taskDto.getStatus().equals("")) {
            throw new EntityBadRequest(" Status is mandatory");
        }else if (taskDto.getPriority() == null || taskDto.getPriority().equals("")) {
            throw new EntityBadRequest(" Priority is mandatory");
        }
        return taskService.updateTask(auth.getName(), id, taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(Authentication auth, @PathVariable Long id) {
        Task task = taskService.getTaskById(auth.getName(), id);
        if(task == null){
            throw new EntityBadRequest(" There is no Task by Id :"+id);
        }
        taskService.deleteTask(task);
        return new ResponseEntity<>( HttpStatus.OK);

    }

    @ExceptionHandler
    public ResponseEntity<EntityErorrResponse> handleEntityNotFoundException(EntityNotFoundException exc) {
        EntityErorrResponse error = new EntityErorrResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<EntityErorrResponse>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EntityErorrResponse> handleEntityAlreadyExistException(EntityAlreadyExist exc) {
        EntityErorrResponse error = new EntityErorrResponse();
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<EntityErorrResponse>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<EntityErorrResponse> handleEntityBadRequestException(EntityBadRequest exc) {
        EntityErorrResponse error = new EntityErorrResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<EntityErorrResponse>(error, HttpStatus.BAD_REQUEST);

    }

}

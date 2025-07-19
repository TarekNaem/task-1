package com.task.respositry;

import com.task.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedByUsername(String username);
    Optional<Task> findByIdAndCreatedByUsername(Long id, String username);
}

package com.task.dto;

import com.task.models.Task;

import java.time.LocalDate;

public class TaskDto {
        private String title;
        private String description;
        private LocalDate dueDate; // or LocalDate depending on what you're sending
        private Task.Status status; // or String depending on how you're sending
        private Task.Priority priority;

        // Getters & Setters


        public Task.Priority getPriority() {
                return priority;
        }

        public void setPriority(Task.Priority priority) {
                this.priority = priority;
        }

        public Task.Status getStatus() {
                return status;
        }

        public void setStatus(Task.Status status) {
                this.status = status;
        }

        public LocalDate getDueDate() {
                return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
                this.dueDate = dueDate;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }
}



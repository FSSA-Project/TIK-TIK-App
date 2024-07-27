package com.fssa.todo.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fssa.todo.model.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    @JsonProperty("user_id")
    private Long userId;
    private UserDto user;

    public TaskDto() {
        // Default constructor
    }

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        if (task.getUser() != null) {
            this.userId = task.getUser().getId();
        }
        this.user = new UserDto(task.getUser());
    }
}


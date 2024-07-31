package com.fssa.todo.Dto;


import com.fssa.todo.model.Task;
import lombok.Data;
import java.util.Date;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Date createdAt;
    private Long userId;
    private UserDto user;


    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.createdAt = task.getCreatedAt();
        if (task.getUser() != null) {
            this.userId = task.getUser().getId();
        }
        assert task.getUser() != null;
        this.user = new UserDto(task.getUser());
    }

    public TaskDto() {
        // Default constructor
    }



}


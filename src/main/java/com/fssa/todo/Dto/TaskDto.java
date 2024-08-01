package com.fssa.todo.Dto;

import com.fssa.todo.Dto.UserDto;
import com.fssa.todo.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate createdAt;
    private LocalDate dueDate;
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
            this.user = new UserDto(task.getUser());
        }
    }
}

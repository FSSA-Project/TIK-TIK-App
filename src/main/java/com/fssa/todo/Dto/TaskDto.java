package com.fssa.todo.Dto;

import com.fssa.todo.Dto.UserDto;
import com.fssa.todo.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private Integer statusId;
    private Long userId;
    private UserDto user;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.createdAt = task.getCreatedAt();
        this.statusId = task.getStatusId().getId();
        if (task.getUser() != null) {
            this.userId = task.getUser().getId();
            this.user = new UserDto(task.getUser());
        }
    }
}

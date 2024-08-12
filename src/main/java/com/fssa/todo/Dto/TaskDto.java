package com.fssa.todo.Dto;

import com.fssa.todo.model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is Mandatory")
    private String description;

    private LocalDate createdAt;
    private LocalDate dueDate;

    @NotNull(message = "StatusId is mandatory")
    private Integer statusId;

    @NotNull(message = "userId is Mandatory")
    private Long userId;
//
//    private UserDto user;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.createdAt = task.getCreatedAt();
        this.dueDate = task.getDueDate();
        this.setUserId(task.getUser().getId());
        this.statusId = task.getTaskStatusId().getId();
    }

}

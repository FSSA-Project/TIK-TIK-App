package com.fssa.todo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusCountDTO {
    private Long todo;
    private Long inProgress;
    private Long completed;
}

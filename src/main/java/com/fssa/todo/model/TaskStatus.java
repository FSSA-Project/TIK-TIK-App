package com.fssa.todo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is for Define the task
 * status and define the id and status
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taskstatus")
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // define a Enum
    private enum Status {
        TODO,
        IN_PROGRESS,
        COMPLETED
    }


}

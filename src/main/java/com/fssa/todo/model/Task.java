package com.fssa.todo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    // Define many to one and this is enum
    @ManyToOne
    @JoinColumn(name = "task_status_id", referencedColumnName = "id")
    private TaskStatus statusId;


    @Column(name = "created_at")
    private LocalDate createdAt;
    private LocalDate dueDate;

    @ManyToOne // many task will user create but one single id unique
    @JsonManagedReference // This act as the parent
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id") // it user_id refers the search the id by tasks table
    private User user;
}

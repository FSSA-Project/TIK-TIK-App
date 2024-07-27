package com.fssa.todo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import java.security.PrivateKey;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;

    @ManyToOne // many task will user create but one single id unique
    @JoinColumn(name = "user_id", nullable = false) // it user_id refers the search the id by tasks table
    @JsonManagedReference // This act as the parent
    private User user;
}

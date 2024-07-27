package com.fssa.todo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;


    // TODO: need to understand the code
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JsonIgnore // Mapped by name is set which model you are connect  private User user - this user refer that;
    private List<Task> task;


    // Below the code is constructor
    public User(Long id, String userName, String email, String passWord) {
        this.id = id;
        this.username = userName;
        this.email = email;
        this.password = passWord;
    }

    public User() {
        // Default constructor
    }
}

package com.fssa.todo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "phonenumber", unique = true)
    private String phoneNumber;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;


    // TODO: need to understand the code
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JsonIgnore // Mapped by name is set which model you are connect  private User user - this user refer that;
    private List<Task> task;


    // Below the code is constructor
    public User(Long id, String name, String email, String password, String phoneNumber, Date dob, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.address = address;
    }

    public User() {
        // Default constructor
    }
}

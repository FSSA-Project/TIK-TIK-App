package com.fssa.todo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is mandatory")
    @Size(min = 6, max = 30, message = "Name must be minimum 7 characters to 30")
    @Column(name = "name")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "email is mandatory")
    @Column(name = "email", unique = true)
    private String email;


    @NotBlank(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
//    @Size(min = 6, max = 12, message = "Password must be 6-12 Characters")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$", message = "Password must be 6-12 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character")
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;

    @Column(name = "profile_link")
    private String profileLink;


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

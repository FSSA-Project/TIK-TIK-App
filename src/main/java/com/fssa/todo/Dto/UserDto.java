package com.fssa.todo.Dto;

import com.fssa.todo.model.User;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;

    public UserDto(User user) {
        if (user != null) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
        }
    }

    // Create a default constructor
    public UserDto(){

    }

}

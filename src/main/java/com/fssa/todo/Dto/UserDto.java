package com.fssa.todo.Dto;

import com.fssa.todo.model.User;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;

    public UserDto(User user) {
        if (user != null) {
            this.id = user.getId();
            this.name = user.getUsername();
            this.email = user.getEmail();
        }
    }


}

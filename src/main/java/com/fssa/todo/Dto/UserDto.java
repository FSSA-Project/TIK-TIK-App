package com.fssa.todo.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fssa.todo.model.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 6, max = 30, message = "Name must be minimum 7 characters to 30")
    private String name;

    @Email(message = "email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 12, message = "Password must be 6-12 Characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$", message = "Password must be 6-12 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String phoneNumber;
    private Date dob;
    private String address;
    private String profileLink;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;

    // Constructor with user obj
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.dob = user.getDob();
        this.address = user.getAddress();

    }
}

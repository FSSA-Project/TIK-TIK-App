package com.fssa.todo.controller;


import com.fssa.todo.ApiReponse.ApiResponse;
import com.fssa.todo.Dto.UserDto;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.exception.UserRegistrationException;
import com.fssa.todo.model.User;
import com.fssa.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Retrive the all users from the DB
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    // Create a new user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto) {
        try {
            // Call service layer
            UserDto createdUserDto = userService.addUser(userDto);

            System.out.println(createdUserDto);
            // Build success response
            ApiResponse<UserDto> response = new ApiResponse<>("User registered successfully", createdUserDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserRegistrationException e) {
            // Build error response with specific message
            ApiResponse<UserDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Handle other exceptions
            ApiResponse<UserDto> response = new ApiResponse<>("An error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
    }


}

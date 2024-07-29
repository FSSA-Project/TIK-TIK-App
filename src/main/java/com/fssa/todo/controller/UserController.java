package com.fssa.todo.controller;


import com.fssa.todo.ApiReponse.ApiResponse;
import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.Dto.UserDto;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.exception.UserRegistrationException;
import com.fssa.todo.jwtutil.JwtUtil;
import com.fssa.todo.model.User;
import com.fssa.todo.service.UserService;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Code for retrive the users from the db
     *
     * @return
     */

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Create a new user
     *
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto) {
        try {
            // Call service layer
            UserDto createdUserDto = userService.addUser(userDto);

            // Build success response
            ApiResponse<UserDto> response = new ApiResponse<>("Registered successfully", createdUserDto);
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


    /**
     * Below the code for login the user
     *
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> loginUser(@RequestBody UserDto userDto) {
        try {
            String email = userDto.getEmail();
            String password = userDto.getPassword();
            UserDto loggedInUser = userService.loginUser(email, password);

            // Generate JWT token
            String token = jwtUtil.generateToken(loggedInUser.getEmail());
            // Set token in the response DTO
            loggedInUser.setToken(token);
            ApiResponse<UserDto> response = new ApiResponse<>("Login success", loggedInUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<UserDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        }
    }

    /**
     * Code for get the  profile to show Cilent
     *
     * @param userDto
     * @return
     */
    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<UserDto>> getUserProfile(@RequestBody UserDto userDto) {
        try {
            UserDto responseDto = userService.getUserProfile(userDto.getEmail());
            ApiResponse<UserDto> response = new ApiResponse<>("Data Retrived Sucess", responseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<UserDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

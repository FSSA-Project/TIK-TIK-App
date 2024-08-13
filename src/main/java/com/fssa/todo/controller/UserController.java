package com.fssa.todo.controller;


import com.fssa.todo.ApiReponse.ApiResponse;
import com.fssa.todo.Dto.UserDto;
import com.fssa.todo.exception.UserRegistrationException;
import com.fssa.todo.jwtutil.jwtService;
import com.fssa.todo.model.User;
import com.fssa.todo.service.JwtBlacklistService;
import com.fssa.todo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
@Validated
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    // To return the empty string in response
    String[] emptyArray = new String[]{};


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
    public ResponseEntity<ApiResponse<?>> createUser(@Valid @RequestBody UserDto userDto) {
        try {
            // Call service layer
            UserDto createdUserDto = userService.addUser(userDto);

            ApiResponse<UserDto> response = new ApiResponse<>("Registered successfully", createdUserDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (UserRegistrationException e) {
            ApiResponse<?> response = new ApiResponse<>(e.getMessage(), emptyArray);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<?> response = new ApiResponse<>(e.getMessage(), emptyArray);
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
    public ResponseEntity<ApiResponse<?>> loginUser(@RequestBody Map<String, String> payload) {
        try {
            // getting from the payload
            String email = payload.get("email");
            String passWord = payload.get("password");

            // This auth is check the password and username
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, passWord));

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(email);

                UserDto loggedInUser = userService.loginUser(email, passWord);

                ApiResponse<?> response = new ApiResponse<>("Login success", loggedInUser, token);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResponse<?> response = new ApiResponse<>("Authentication failed", emptyArray);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException e) {
            ApiResponse<?> response = new ApiResponse<>(e.getMessage(), emptyArray);
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
    public ResponseEntity<ApiResponse<UserDto>> getUserProfile(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto responseDto = userService.getUserProfile(userDto.getEmail());
            ApiResponse<UserDto> response = new ApiResponse<>("Data Retrived Sucess", responseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<UserDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Below the code for logout the user
     * code
     *
     * @param request Parameter get the header from the
     *  Authorization
     * @return the Message Logout
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        ApiResponse<String> response;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            jwtBlacklistService.blacklistToken(token);
            SecurityContextHolder.clearContext();
            response = new ApiResponse<>("Successfully logged out", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ApiResponse<>("Token is missing", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


}

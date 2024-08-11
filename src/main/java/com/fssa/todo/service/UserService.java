package com.fssa.todo.service;


import com.fssa.todo.Dto.UserDto;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.exception.UserRegistrationException;
import com.fssa.todo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // set the strength as 12 rounds

    /**
     * Code for get the all user from the
     * DB
     *
     * @return
     */
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return new ResponseEntity<>(userDao.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This is for insert the user data into the
     * database
     *
     * @Return String
     */
    public UserDto addUser(UserDto userDto) {

        User existingUser = userDao.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            throw new UserRegistrationException("Email already exists");
        }

        // Set the user model
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword())); // Encode the password

        User savedUser = userDao.save(user);

        // Convert saved entity to DTO in Response obj
        UserDto savedUserDto = new UserDto();
        savedUserDto.setId(savedUser.getId());
        savedUserDto.setName(savedUser.getName());
        savedUserDto.setEmail(savedUser.getEmail());

        return savedUserDto;
    }

    /**
     * Below the code for login User
     *
     * @param userDto
     * @return
     */
    public UserDto loginUser(String email, String password) {

        User user = userDao.findByEmail(email);
        if (user != null && encoder.matches(password, user.getPassword())) { // Check the password matches
            // Create a new UserDto
            UserDto responseDto = new UserDto();
            responseDto.setId(user.getId());
            responseDto.setName(user.getName());
            responseDto.setEmail(user.getEmail());

            return responseDto;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }


    /**
     * Code for get the profile from the DB
     *
     * @param email
     * @return
     */
    public UserDto getUserProfile(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            // Create a obj of userDto
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDto.setName(user.getName());
            userDto.setDob(user.getDob());
            userDto.setAddress(user.getAddress());
            userDto.setPassword(user.getPassword());

            return userDto;
        } else {
            throw new RuntimeException("Invaild email or cannot find");
        }
    }
}




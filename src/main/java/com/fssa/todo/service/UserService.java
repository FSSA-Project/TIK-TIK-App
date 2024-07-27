package com.fssa.todo.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fssa.todo.ApiReponse.ApiResponse;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;


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
     */

    public ResponseEntity<String> addUser(User user) {
        try {
            userDao.save(user);
            return new ResponseEntity<>("Data Store in DB", HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

}

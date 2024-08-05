package com.fssa.todo.service;

import com.fssa.todo.dao.UserDao;
import com.fssa.todo.model.User;
import com.fssa.todo.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * This class implements the userdetails service and
 * use the override for give the method and return the
 * object
 */

@Service
public class MyUserDetailsService implements UserDetailsService  {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.findByEmail(email); // Intract with the DB and search the name

        if(user == null){
            System.out.println("User 404");
            throw  new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}

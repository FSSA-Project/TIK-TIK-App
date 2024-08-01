package com.fssa.todo.dao;


import com.fssa.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import java.sql.SQLException;


@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByName(String name);
    User findByEmail(String email);

}

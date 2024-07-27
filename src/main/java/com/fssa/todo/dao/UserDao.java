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


    /**
     * this method convert result set data into DTO
     *
     * @param rs
     * @return
     * @throws SQLException
     */
//    private User convert(ResultSet rs) throws SQLException {
//        User user = new User();
//        user.setId(rs.getInt("id"));
//        user.setUserName(rs.getString("name"));
//        user.setEmail(rs.getString("password"));
//        user.setPassWord(rs.getString("email"));
//        return user;
//    }

//    private static class UserRowMapper implements RowMapper<User> {
//        @Override
//        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//            User user = new User();
//            user.setId(rs.getInt("id"));
//            user.setUserName(rs.getString("name"));
//            user.setEmail(rs.getString("email"));
//            user.setPassWord(rs.getString("password"));
//            return user;
//        }
//    }
}

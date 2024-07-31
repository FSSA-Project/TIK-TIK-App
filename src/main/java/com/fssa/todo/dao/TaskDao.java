package com.fssa.todo.dao;

import com.fssa.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskDao  extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.user_id = :userId")
   List<Task> findByUserId(@Param("userId") Long userId);

}

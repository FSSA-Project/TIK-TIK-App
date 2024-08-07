package com.fssa.todo.dao;

import com.fssa.todo.Dto.TaskStatusCountDTO;
import com.fssa.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskDao extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    @Query("SELECT new com.fssa.todo.Dto.TaskStatusCountDTO(" +
            "SUM(CASE WHEN t.taskStatusId.id = 1 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN t.taskStatusId.id = 2 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN t.taskStatusId.id = 3 THEN 1 ELSE 0 END)) " +
            "FROM Task t WHERE t.user.id = :userId")
    TaskStatusCountDTO getTaskCountsByStatus(@Param("userId") Long userId);

}

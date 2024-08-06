package com.fssa.todo.dao;

import com.fssa.todo.model.Task;
import com.fssa.todo.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This class is for intract with the
 * database
 */
public interface TaskStatusDao extends JpaRepository<TaskStatus, Integer> {

    // Create a new method that is find the task id
    Optional<TaskStatus> findByStatus(String name);


}

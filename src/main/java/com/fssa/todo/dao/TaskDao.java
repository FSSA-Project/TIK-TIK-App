package com.fssa.todo.dao;

import com.fssa.todo.model.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskDao  extends JpaRepository<Task, Long> {

}

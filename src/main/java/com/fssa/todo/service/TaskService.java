package com.fssa.todo.service;


import com.fssa.todo.ApiReponse.ApiResponse;
import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.dao.TaskDao;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.model.Task;
import com.fssa.todo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {


    @Autowired
    private TaskDao taskDao;

    @Autowired
    private UserDao userDao;

    /**
     * Here the code for create a new
     * task
     *
     * @param task
     * @return
     */
    public TaskDto createTask(TaskDto taskDto) {

        // Set to the task model
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());

        System.out.println("Received TaskDto: " + taskDto);

        User user = userDao.findById(taskDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        Task savedTask = taskDao.save(task);
        return new TaskDto(savedTask);
    }


    public List<Task> listAllTasks() {
        return taskDao.findAll();
    }


    // Code for update the task
    public TaskDto updateTask(Long id, TaskDto taskDto) {

        Task existingtask = taskDao.findById(id).orElseThrow(() -> new RuntimeException("Task is not found"));
        existingtask.setTitle(taskDto.getTitle());
        existingtask.setDescription(taskDto.getDescription());
        existingtask.setStatus(taskDto.getStatus());

        Task updatedTask = taskDao.save(existingtask);
        return new TaskDto(updatedTask);

    }

    // Below the code for delete the task
    public void deleteTask(Long id) {
        if (taskDao.existsById(id)) {
            taskDao.deleteById(id);
        } else {
            throw new RuntimeException("task not found");
        }
    }
}

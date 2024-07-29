package com.fssa.todo.service;


import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.dao.TaskDao;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.model.Task;
import com.fssa.todo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

        User user = userDao.findById(taskDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        Task savedTask = taskDao.save(task);
        return new TaskDto(savedTask);
    }

    /**
     * Below the code for list all the tasks
     *
     * @return
     */
    public List<Task> listAllTasks() {
        return taskDao.findAll();
    }


    /**
     * Below the code for update the task
     *
     * @param id
     * @param taskDto
     * @return
     */
    public TaskDto updateTask(Long id, TaskDto taskDto) {

        Task existingtask = taskDao.findById(id).orElseThrow(() -> new RuntimeException("Task is not found"));
        existingtask.setId(taskDto.getId());
        existingtask.setTitle(taskDto.getTitle());
        existingtask.setDescription(taskDto.getDescription());
        existingtask.setStatus(taskDto.getStatus());
        existingtask.setCreatedAt(taskDto.getCreatedAt());

        Task updatedTask = taskDao.save(existingtask);
        return new TaskDto(updatedTask);

    }

    /**
     * below the code for delete the task
     *
     * @param id
     */
    public void deleteTask(Long id) {
        if (taskDao.existsById(id)) {
            taskDao.deleteById(id);
        } else {
            throw new RuntimeException("task not found");
        }
    }


    /**
     * Below the code for get the task by id
     *
     * @param id
     * @return
     */
    public TaskDto getTaskById(Long id) {
        // check the validation
        if (id != null && id > 0) {
            // Create a new obj in taskdto
            TaskDto taskDto = new TaskDto();
            Task existingTask = taskDao.findById(id).orElseThrow(() -> new RuntimeException("Task is not found"));
            taskDto.setId(existingTask.getId());
            taskDto.setTitle(existingTask.getTitle());
            taskDto.setDescription(existingTask.getDescription());
            taskDto.setStatus(existingTask.getStatus());
            taskDto.setCreatedAt(existingTask.getCreatedAt());
            taskDto.setUserId(existingTask.getId());

            return taskDto;
        } else {
            throw new RuntimeException("Validation error!");
        }
    }



    /**
     * Below the code for update the status by the id
     *
     * @param id
     * @param status
     * @return
     */

    public TaskDto updateStatusById(Long id, String status) {
        // Check the validation
        if (id != null && id > 0 && !status.isBlank() && !status.isEmpty()) {
            // Create a TaskDTO method
            TaskDto taskDto = new TaskDto();
            Task existingTask = taskDao.findById(id).orElseThrow(() -> new RuntimeException("Task is not found"));
            taskDto.setId(id);
            taskDto.setTitle(existingTask.getTitle());
            taskDto.setDescription(existingTask.getDescription());
            taskDto.setStatus(status);
            taskDto.setCreatedAt(taskDto.getCreatedAt());
            taskDto.setUserId(id);

            return taskDto;
        } else {
            throw new RuntimeException("Validation error");
        }

    }
}

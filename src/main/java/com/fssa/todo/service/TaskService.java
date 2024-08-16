package com.fssa.todo.service;


import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.Dto.TaskStatusCountDTO;
import com.fssa.todo.dao.TaskDao;
import com.fssa.todo.dao.TaskStatusDao;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.model.Task;
import com.fssa.todo.model.TaskStatus;
import com.fssa.todo.model.User;
import org.hibernate.id.factory.internal.StandardIdentifierGeneratorFactoryInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fssa.todo.jwtutil.jwtService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {


    @Autowired
    private TaskDao taskDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskStatusDao taskStatusDao;

    @Autowired
    private jwtService jwtService;

    /**
     * Here the code for create a new
     * task
     *
     * @param taskDto
     * @return
     */
    public TaskDto createTask(TaskDto taskDto, String token) {

        // Extract the email from the token
        String email = jwtService.extractEmail(token);

        // Find the user by email
        User user = userDao.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Create a new Task entity and set its fields
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setCreatedAt(LocalDate.now());
        task.setUser(user);

        // Set the task status if provided
        if (taskDto.getStatusId() != null && taskDto.getStatusId() > 0) {
            TaskStatus taskStatus = taskStatusDao.findById(taskDto.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Task status not found"));
            task.setTaskStatusId(taskStatus);
        }

        Task savedTask = taskDao.save(task);
        return new TaskDto(savedTask);
    }


    /**
     * Below the code for update the task
     *
     * @param id
     * @param taskDto
     * @return
     */

    public TaskDto updateTask(Long id, TaskDto taskDto, String token) {
        if (id == null || id < 0 || taskDto == null || token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Invalid id or token");
        }

        // Extract the email from the token and get the userId
        String email = jwtService.extractEmail(token);

        // Fetch the user by email
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not Found");
        }

        // Find the existing task
        Task existingTask = taskDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setDueDate(taskDto.getDueDate());
        existingTask.setUser(user);

        // Update the status if a valid status ID is provided
        if (taskDto.getStatusId() != null && taskDto.getStatusId() > 0) {
            TaskStatus taskStatus = taskStatusDao.findById(taskDto.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Task status not found"));
            existingTask.setTaskStatusId(taskStatus);
        }

        Task updatedTask = taskDao.save(existingTask);
        return new TaskDto(updatedTask);
    }


    /**
     * Below the code for update the status by the id
     *
     * @param taskId
     * @param statusId
     * @return updatedTask
     */

    public TaskDto updateStatusById(Long taskId, int statusId) {
        if (taskId == null || taskId <= 0 || statusId <= 0) {
            throw new IllegalArgumentException("Invalid task ID or status ID");
        }

        // Find the existing task
        Task task = taskDao.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        // Find the task status by ID
        TaskStatus taskStatus = taskStatusDao.findById(statusId).orElseThrow(() -> new RuntimeException("TaskStatus not found"));

        // Update the task's status and set the user
        task.setTaskStatusId(taskStatus);

        // Save the updated task
        Task updatedTask = taskDao.save(task);

        // Convert to TaskDto
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
     * @param token
     * @return
     */
    public List<TaskDto> listTasksByUserId(String token) {

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token is error!");
        }

        // Extract the token
        String email = jwtService.extractEmail(token);

        // Find the userId by their mail
        User user = userDao.findByEmail(email);

        List<Task> tasks = taskDao.findByUserId(user.getId());
        if (tasks == null || tasks.isEmpty()) {
            throw new RuntimeException("No tasks found for the user");
        }

        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto taskDto = new TaskDto(task);
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }


    public TaskStatusCountDTO getTaskCountsByStatus(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        TaskStatusCountDTO taskStatusCountDTO = taskDao.getTaskCountsByStatus(userId);
        if (taskStatusCountDTO != null) {
            return taskStatusCountDTO;
        }
        throw new RuntimeException("No task data found for user ID");
    }

    public TaskDto getTaskById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid task ID: " + id);
        }

        Task task = taskDao.findTaskById(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id: " + id);
        }

        return new TaskDto(task);
    }

    /**
     * Below the code for search in the DB
     *
     * @param query to get the Search filter
     * @return the Data from the database and give the
     * data to the users
     */
    public List<TaskDto> searchTasks(String query, String token) {

        // Extract the token from the database
        String email = jwtService.extractEmail(token);

        // Find the email using email id
        User user = userDao.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User is not Found");
        }
        List<Task> tasks = taskDao.searchByTitleOrDescription(query, user.getId());

        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto taskDto = new TaskDto();

            taskDto.setId(task.getId());
            taskDto.setTitle(task.getTitle());
            taskDto.setDescription(task.getDescription());
            taskDto.setCreatedAt(task.getCreatedAt());
            taskDto.setDueDate(task.getDueDate());
            taskDto.setStatusId(task.getTaskStatusId().getId());
            taskDto.setUserId(task.getUser().getId());

            taskDtos.add(taskDto);

        }
        return taskDtos;
    }

}

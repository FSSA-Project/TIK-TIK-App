package com.fssa.todo.service;


import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.Dto.TaskStatusCountDTO;
import com.fssa.todo.dao.TaskDao;
import com.fssa.todo.dao.TaskStatusDao;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.model.Task;
import com.fssa.todo.model.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    /**
     * Here the code for create a new
     * task
     *
     * @param taskDto
     * @return
     */
    public TaskDto createTask(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());

        // Set the status
        if (taskDto.getStatusId() != null && taskDto.getStatusId() > 0) {
            TaskStatus taskStatus = taskStatusDao.findById(taskDto.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Task status not found"));
            task.setTaskStatusId(taskStatus);
        }

//        // Set the user
//        if (taskDto.getUserId() != null && taskDto.getUserId() > 0) {
//            User user = userDao.findById(taskDto.getUserId())
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//            task.setUser(user);
//        }

        // Set created at date
        task.setCreatedAt(LocalDate.now());

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

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task existingTask = taskDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setDueDate(taskDto.getDueDate());

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
     * @param userId
     * @return
     */
    public List<TaskDto> listTasksByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new RuntimeException("Validation error!");
        }

        List<Task> tasks = taskDao.findByUserId(userId);
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

        // Update the task's status
        task.setTaskStatusId(taskStatus);

        // Save the updated task
        Task updatedTask = taskDao.save(task);

        // Convert to TaskDto
        return new TaskDto(updatedTask);
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
     * @param request
     * @return
     */
    public List<TaskDto> searchTasks(String query) {
        List<Task> tasks = taskDao.searchByTitleOrDescription(query);
        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            taskDtos.add(convertToDto(task));
        }
        return taskDtos;
    }

    private TaskDto convertToDto(Task task) {
        // Convert Task entity to TaskDto
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatusId(task.getTaskStatusId().getId());
//        taskDto.setUserId(task.getUser().getId());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setDueDate(task.getDueDate());
        return taskDto;
    }
}
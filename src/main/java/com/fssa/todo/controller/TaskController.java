package com.fssa.todo.controller;


import com.fssa.todo.ApiReponse.ApiResponse;
import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.dao.TaskDao;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.model.Task;
import com.fssa.todo.service.TaskService;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserDao userDao;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@RequestBody TaskDto taskDto) {
        try {

            if (taskDto.getUserId() == null || !userDao.existsById(taskDto.getUserId())) {
                return new ResponseEntity<>(new ApiResponse<>("Invalid user ID", null), HttpStatus.BAD_REQUEST);
            }
            TaskDto createdTask = taskService.createTask(taskDto);
            ApiResponse<TaskDto> response = new ApiResponse<>("Task successfully created", createdTask);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Below the code get the data
    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<Task>>> listAllTasks() {
        try {
            List<Task> tasks = taskService.listAllTasks();
            ApiResponse<List<Task>> response = new ApiResponse<>("Data Retrived SucessFully", tasks);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<Object> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Code for update the task
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(@RequestBody TaskDto taskDto) {
        try {
            TaskDto updatedTask = taskService.updateTask(taskDto.getId(), taskDto);
            ApiResponse<TaskDto> response = new ApiResponse<>("Task updated SucessFully", taskDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Code for delete the task
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<ApiResponse<Long>> deleteTask(@RequestBody Map<String, Object> payload) {
        try {
            Long id = Long.parseLong(payload.get("id").toString()); // Extract the ID from the payload
            taskService.deleteTask(id); // Call service to delete task
            ApiResponse<Long> response = new ApiResponse<>("Task successfully deleted", id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NumberFormatException e) {
            ApiResponse<Long> response = new ApiResponse<>("Invalid ID format", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            ApiResponse<Long> response = new ApiResponse<>("Task not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Long> response = new ApiResponse<>("An error occurred while deleting the task", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

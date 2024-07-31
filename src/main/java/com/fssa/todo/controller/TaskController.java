package com.fssa.todo.controller;


import com.fssa.todo.ApiReponse.ApiResponse;
import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.dao.UserDao;
import com.fssa.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    /**
     * Code for create a new Task
     *
     * @param taskDto
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@RequestBody TaskDto taskDto) {
        try {

            if (taskDto.getUserId() == null || taskDto.getUserId() > 0 || !userDao.existsById(taskDto.getUserId())) {
                return new ResponseEntity<>(new ApiResponse<>("Userid doesn't exists", null), HttpStatus.BAD_REQUEST);
            }
            TaskDto createdTask = taskService.createTask(taskDto);
            ApiResponse<TaskDto> response = new ApiResponse<>("Task successfully created", createdTask);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Code for Get all the tasks
     *
     * @return
     */
    @PostMapping("/tasks")
    public ResponseEntity<ApiResponse<List<TaskDto>>> listTasksByUserId(@RequestBody TaskDto taskDto) {
        try {
            System.out.println(taskDto.getUserId());
            List<TaskDto> tasks = taskService.listTasksByUserId(taskDto.getUserId());
            ApiResponse<List<TaskDto>> response = new ApiResponse<>("Data Retrieved Successfully", tasks);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<List<TaskDto>> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<List<TaskDto>> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Below the code for get the task by id
     *
     * @param taskDto
     * @return
     */

    @GetMapping
    public ResponseEntity<ApiResponse<TaskDto>> getTaskById(@RequestBody TaskDto taskDto) {
        try {
            Long id = taskDto.getId();
            TaskDto getTaskById = taskService.getTaskById(taskDto.getId());
            System.out.println(taskDto.getId());
            ApiResponse<TaskDto> response = new ApiResponse<>("Data Retrived Successfully", getTaskById);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Below the controller code for update the task by status by id
     *
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/update/status")
    public ResponseEntity<ApiResponse<TaskDto>> updateStatusById(@RequestBody TaskDto taskDto) {
        try {
            Long taskId = taskDto.getId();
            String status = taskDto.getStatus();

            TaskDto updateStatusById = taskService.updateStatusById(taskId, status);
            ApiResponse<TaskDto> response = new ApiResponse<>("Data Updated SuccessFully", updateStatusById);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Code for update the task
     *
     * @param taskDto
     * @return
     */
    @PutMapping("/update")
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


    /**
     * code for Delete the task
     *
     * @param payload
     * @return
     */
    @DeleteMapping("/delete")
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

package com.fssa.todo.controller;


import com.fssa.todo.ApiReponse.ApiResponse;
import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.Dto.TaskStatusCountDTO;
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
            if (taskDto.getUserId() == null || !userDao.existsById(taskDto.getUserId())) {
                return new ResponseEntity<>(new ApiResponse<>("UserId doesn't exist", null), HttpStatus.BAD_REQUEST);
            }
            // Proceed with task creation
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
    public ResponseEntity<ApiResponse<List<TaskDto>>> listTasksByUserId(@RequestBody Map<String, Long> id) {
        try {
            Long userId = id.get("id");
            List<TaskDto> tasks = taskService.listTasksByUserId(userId);
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
     * Below the controller code for update the task by status by id
     *
     * @param id
     * @param taskDto
     * @return
     */
    @PostMapping("/update/status")
    public ResponseEntity<ApiResponse<TaskDto>> updateStatusById(@RequestBody TaskDto taskDto) {
        try {
            Long taskId = taskDto.getId();
            int statusId = taskDto.getStatusId();

            TaskDto updateStatusById = taskService.updateStatusById(taskId, statusId);
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
    @PatchMapping("/update")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(@RequestBody TaskDto taskDto) {
        try {
            TaskDto updatedTask = taskService.updateTask(taskDto.getId(), taskDto);
            ApiResponse<TaskDto> response = new ApiResponse<>("Task updated SuccessFully", updatedTask);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<TaskDto> response = new ApiResponse<>(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/id")
    public ResponseEntity<ApiResponse<TaskDto>> getTaskById(@RequestBody TaskDto taskDto) {
        try {
            TaskDto dto = taskService.getTaskById(taskDto.getId());
            ApiResponse<TaskDto> response = new ApiResponse<>("Task updated SuccessFully", dto);
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

    @PostMapping("/task-counts")
    public ResponseEntity<ApiResponse<TaskStatusCountDTO>> getTaskCountsByStatus(@RequestBody Map<String, Object> payload) {
        try {
            // Extract the userId from the payload map
            Long userId = Long.parseLong(payload.get("userId").toString());

            // Call the service to get the task counts by status
            TaskStatusCountDTO taskStatusCountDTO = taskService.getTaskCountsByStatus(userId);

            // Create a successful response
            ApiResponse<TaskStatusCountDTO> response = new ApiResponse<>("Task count retrieved successfully", taskStatusCountDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NumberFormatException e) {
            // Handle invalid userId format
            ApiResponse<TaskStatusCountDTO> response = new ApiResponse<>("Invalid user ID format", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            // Handle other runtime exceptions
            ApiResponse<TaskStatusCountDTO> response = new ApiResponse<>("Task count retrieval failed", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<TaskStatusCountDTO> response = new ApiResponse<>("An error occurred while retrieving task counts", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

package com.fssa.todo.controllertest;

import com.fssa.todo.Dto.TaskDto;
import com.fssa.todo.controller.TaskController;
import com.fssa.todo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;


    // This is the setup controller
    public TaskControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

    }

    @Test
    public void testGetAllTasksByUserId() throws Exception {
        Long userId = 1L;

        List<TaskDto> taskDtos = List.of(
                new TaskDto(1L, "test title", "test Description", LocalDate.now(),
                        null, 1));

        when(taskService.listTasksByUserId(userId)).thenReturn(taskDtos);


        // If you want to debug give MvcResult and get the responsebody
        MvcResult result = mockMvc.perform(post("/api/v1/task/tasks").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": " + userId + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(taskDtos.size()))
                .andReturn();

//        String responseBody = result.getResponse().getContentAsString(); to debug

        verify(taskService, times(1)).listTasksByUserId(userId);
    }

    public void CreateTaskTest() throws Exception {


    }


}

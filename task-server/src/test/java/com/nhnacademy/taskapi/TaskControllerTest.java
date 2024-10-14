package com.nhnacademy.taskapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.controller.TaskController;
import com.nhnacademy.taskapi.dto.request.TaskPostDto;
import com.nhnacademy.taskapi.dto.request.TaskPutDto;
import com.nhnacademy.taskapi.dto.response.TaskResponseDto;
import com.nhnacademy.taskapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = TaskController.class) // TaskController만 테스트 대상으로
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;


    @Autowired
    private ObjectMapper objectMapper;
    private TaskPostDto taskPostDto;

    @BeforeEach
    void setUp() {
        taskPostDto = new TaskPostDto(1L, null, "테스트 작업");
    }

    @Test
    void testCreateTask() throws Exception {

        when(taskService.createTask(any(Long.class), any(Long.class), any(TaskPostDto.class)))
                .thenReturn(new TaskResponseDto(1L, 1L, null, "테스트 작업"));

        // POST 요청 테스트
        mockMvc.perform(post("/projects/1/tasks")
                        .header("accountId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskPostDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.taskContent").value("테스트 작업"));
    }

    @Test
    void testGetTasks() throws Exception {
        when(taskService.getTasksByProjectId(any(Long.class), any(Long.class)))
                .thenReturn(List.of(
                        new TaskResponseDto(1L, 1L, null, "테스트 작업 1"),
                        new TaskResponseDto(2L, 1L, null, "테스트 작업 2")));

        // GET 요청 테스트
        mockMvc.perform(get("/projects/1/tasks")
                        .header("accountId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].taskContent").value("테스트 작업 1"))
                .andExpect(jsonPath("$.data[1].taskContent").value("테스트 작업 2"));
    }

    @Test
    void testUpdateTask() throws Exception {
        TaskPutDto taskPutDto = new TaskPutDto(1L, "업데이트된 작업");

        when(taskService.updateTask(any(Long.class), any(Long.class), any(Long.class), any(TaskPutDto.class)))
                .thenReturn(new TaskResponseDto(1L, 1L, null, "업데이트된 작업"));

        // PUT 요청 테스트
        mockMvc.perform(put("/projects/1/tasks/1")
                        .header("accountId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskPutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.taskContent").value("업데이트된 작업"));
    }

    @Test
    void testDeleteTask() throws Exception {
        // DELETE 요청 테스트
        mockMvc.perform(delete("/projects/1/tasks/1")
                        .header("accountId", "1"))
                .andExpect(status().isNoContent());
    }
}

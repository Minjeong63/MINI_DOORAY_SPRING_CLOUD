package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.dto.request.TaskPostDto;
import com.nhnacademy.taskapi.dto.request.TaskPutDto;
import com.nhnacademy.taskapi.dto.response.TaskResponseDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.exception.ProjectNotFoundException;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = mock(Project.class);
        when(project.getProjectId()).thenReturn(1L);

        // Task 객체 생성
        task = new Task();
        task.setTaskId(1L);
        task.setTaskContent("테스트 작업");
        task.setProject(project);  // Task에 Project 설정
    }

//    @Test
//    void testCreateTask() {
//        TaskPostDto taskPostDto = new TaskPostDto(1L, null, "새 작업");
//
//        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
//        when(taskRepository.save(any(Task.class))).thenReturn(task);
//
//        TaskResponseDto taskResponseDto = taskService.createTask(1L, null, taskPostDto);
//
//        assertEquals("새 작업", taskResponseDto.taskContent());
//    }

    @Test
    void testGetTasksByProjectId() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.findByProject(project)).thenReturn(List.of(task));

        List<TaskResponseDto> tasks = taskService.getTasksByProjectId(1L, null);

        assertEquals(1, tasks.size());
        assertEquals("테스트 작업", tasks.get(0).taskContent());
    }

//
//    @Test
//    void testUpdateTask() {
//        TaskPutDto taskPutDto = new TaskPutDto(1L, "업데이트된 작업");
//
//        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
//        Task task = new Task();
//        task.setTaskId(1L); // Task ID 설정
//        task.setTaskContent("이전 작업 내용");
//        task.setProject(project); // Project 설정
//
//        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
//
//        System.out.println("Task ID: " + task.getTaskId());
//        System.out.println("Project ID: " + task.getProject().getProjectId());
//
//        TaskResponseDto updatedTask = taskService.updateTask(1L, 1L, null, taskPutDto);
////        assertEquals("업데이트된 작업", updatedTask.taskContent());
//    }



    @Test
    void testDeleteTask() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L, 1L, null);

        verify(taskRepository, times(1)).delete(task);
    }
}

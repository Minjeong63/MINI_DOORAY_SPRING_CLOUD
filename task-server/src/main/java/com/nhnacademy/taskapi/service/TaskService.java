package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.TaskPostDto;
import com.nhnacademy.taskapi.dto.request.TaskPutDto;
import com.nhnacademy.taskapi.dto.response.TaskResponseDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.exception.ProjectNotFoundException;
import com.nhnacademy.taskapi.exception.TaskBelongsToAnotherProjectException;
import com.nhnacademy.taskapi.exception.TaskNotFoundException;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    // 프로젝트 ID로 프로젝트에 속한 모든 Task 조회
    public List<TaskResponseDto> getTasksByProjectId(Long projectId, Long accountId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        return taskRepository.findByProject(project).stream()
                .map(this::toTaskResponseDto)
                .collect(Collectors.toList());
    }

    // Task 생성
    public TaskResponseDto createTask(Long projectId, Long accountId, TaskPostDto taskPostDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        Task task = new Task();
        task.setProject(project);
        task.setTaskContent(taskPostDto.taskContent());

        Task createdTask = taskRepository.save(task);
        return toTaskResponseDto(createdTask);
    }

    // Task 수정 (TaskPutDto로 입력 받고 TaskResponseDto로 반환)
    public TaskResponseDto updateTask(Long projectId, Long taskId, Long accountId, TaskPutDto taskPutDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        if (!task.getProject().getProjectId().equals(projectId)) {
            throw new TaskBelongsToAnotherProjectException(taskId, projectId);
        }

        task.setTaskContent(taskPutDto.taskContent());

        Task updatedTask = taskRepository.save(task);
        return toTaskResponseDto(updatedTask);
    }

    // Task 삭제
    public void deleteTask(Long projectId, Long taskId, Long accountId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        if (!task.getProject().getProjectId().equals(projectId)) {
            throw new TaskBelongsToAnotherProjectException(taskId, projectId);
        }

        taskRepository.delete(task);
    }

    private TaskResponseDto toTaskResponseDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto(task.getTaskId(),task.getProject().getProjectId(),task.getMilestone(),task.getTaskContent());
//        dto.setTaskId(task.getTaskId());
//        dto.setProjectId(task.getProject().getProjectId());
//        dto.setTaskContent(task.getTaskContent());
        return dto;
    }
}

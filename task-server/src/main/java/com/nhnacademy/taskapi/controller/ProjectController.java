package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.request.ProjectDto;
import com.nhnacademy.taskapi.dto.request.ProjectMakeDto;
import com.nhnacademy.taskapi.dto.request.ProjectMemberDto;
import com.nhnacademy.taskapi.dto.request.ProjectUpdateDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {


    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //프로젝트 가져오기
    @GetMapping
    public ResponseEntity<DefaultDto<List<ProjectDto>>> getAllProjects(@RequestHeader("accountId") Long accountId) {
        List<ProjectDto> projects = projectService.getProjectsByAccountId(accountId);
        DefaultDto<List<ProjectDto>> defaultDto = new DefaultDto<>(200, projects);
        return ResponseEntity.status(HttpStatus.CREATED).body(defaultDto);
    }
    //프로젝트 등록
    @PostMapping
    public ResponseEntity<DefaultDto<Object>> registerProject(@RequestHeader("accountId") Long accountId,
                                                      @RequestBody ProjectMakeDto projectDto) {
        projectService.saveProject(accountId, projectDto);
        DefaultDto<Object> dto = new DefaultDto<>(201, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //프로젝트 수정
    @PutMapping("/{projectId}")
    public ResponseEntity<DefaultDto<ProjectDto>> updateProject(
            @PathVariable Long projectId, // 수정할 프로젝트 ID
            @RequestBody ProjectUpdateDto projectDto, // 수정할 프로젝트 내용
            @RequestHeader("accountId") Long accountId
    ) {
        ProjectDto updatedProject = projectService.updateProject(accountId, projectId, projectDto);
        DefaultDto<ProjectDto> dto = new DefaultDto<>(200, updatedProject);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //프로젝트 삭제
    @DeleteMapping("/{projectId}")
    public ResponseEntity<DefaultDto<Object>> deleteProject(@RequestHeader("accountId") Long accountId,
                                                             @PathVariable Long projectId) {
        projectService.deleteProject(accountId, projectId);
        DefaultDto<Object> dto = new DefaultDto<>(200, null);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/{projectId}/members")
    public ResponseEntity<DefaultDto<Object>> addMemberToProject(
            @PathVariable Long projectId,  // 프로젝트 ID
            @RequestBody ProjectMemberDto projectMemberDto,   // 계정 ID
            @RequestHeader("accountId") Long requestingAccountId
    ) {
        projectService.addMemberToProject(requestingAccountId, projectMemberDto.accountId(), projectId);
        DefaultDto<Object> dto = new DefaultDto<>(201, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<DefaultDto<ProjectDto>> getProject(@RequestHeader("accountId") Long accountId,
                                                 @PathVariable Long projectId) {
        ProjectDto project = projectService.getProject(accountId, projectId);
        DefaultDto<ProjectDto> defaultDto = new DefaultDto<>(200, project);
        return ResponseEntity.status(HttpStatus.CREATED).body(defaultDto);
    }

}
package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.ProjectDto;
import com.nhnacademy.taskapi.dto.request.ProjectMakeDto;
import com.nhnacademy.taskapi.dto.request.ProjectUpdateDto;
import com.nhnacademy.taskapi.entity.*;
import com.nhnacademy.taskapi.exception.AccountNotMemberException;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.repository.AccountRepository;
import com.nhnacademy.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectMemberRepository projectMemberRepository, AccountRepository accountRepository) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.accountRepository = accountRepository;
    }

    // 프로젝트 생성 및 관리자 설정
    public void saveProject(Long accountId, ProjectMakeDto projectDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));

        Project project = new Project(account, projectDto.projectName(), ProjectStatus.ACTIVE);
        projectRepository.save(project);
    }

    public ProjectDto updateProject(Long accountId, Long projectId, ProjectUpdateDto projectDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));

        if (!project.getManager().getAccountId().equals(account.getAccountId())){
            throw new IllegalArgumentException("Only manager can delete project.");
        }

        // 3. 프로젝트 필드 업데이트
        project.setProjectName(projectDto.projectName());
        project.setProjectStatus(projectDto.projectStatus());
        projectRepository.save(project);

        return new ProjectDto(project.getProjectId(), project.getManager().getAccountId(), project.getProjectName(), project.getProjectStatus());
    }

    @Transactional
    public void deleteProject(Long accountId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));
        if (!project.getManager().getAccountId().equals(account.getAccountId())){
            throw new IllegalArgumentException("Only manager can delete project.");
        }

        projectMemberRepository.deleteByProject(project);
        projectRepository.delete(project);
    }


    public void addMemberToProject(Long accountId, Long registerAccountId, Long projectId) {
        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID:" + projectId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID: " + accountId));

        if(!project.getManager().getAccountId().equals(accountId)) {
            throw new AccountNotMemberException(accountId);
        }

        if (accountId.equals(registerAccountId)){
            throw new IllegalArgumentException("You cannot add yourself as a project member.");
        }

        //account 안에 있는지 확인
        Account registerAccount = accountRepository.findById(registerAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID: " + registerAccountId));

        //프로젝트 멤버에 이미 있는지 확인
        if (projectMemberRepository.existsByProjectAndMember(project, registerAccount)) {
            throw new IllegalArgumentException("This account is already a member of the project.");
        }

        //프로젝트멤버 추가
        ProjectMember newProjectMember = new ProjectMember(project, registerAccount);
        projectMemberRepository.save(newProjectMember);
    }

    // 사용자가 속한 프로젝트 목록 조회
    public List<ProjectDto> getProjectsByAccountId(Long accountId) {
        List<ProjectDto> projectList = new ArrayList<>();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID" + accountId));
        for (Project project : account.getProjects()){
            ProjectDto projectDto = new ProjectDto(
                    project.getProjectId(),
                    project.getManager().getAccountId(),
                    project.getProjectName(),
                    project.getProjectStatus());
            projectList.add(projectDto);
        }
        for (Project project : projectMemberRepository.findProjectsByAccountId(accountId)){
            ProjectDto projectDto = new ProjectDto(
                    project.getProjectId(),
                    project.getManager().getAccountId(),
                    project.getProjectName(),
                    project.getProjectStatus());
            projectList.add(projectDto);
        }

        return projectList;
    }

    public ProjectDto getProject(Long accountId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID:" + projectId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account" + "ID: " + accountId));
        if (projectMemberRepository.existsByProjectAndMember(project, account)) {
            throw new IllegalArgumentException("This account is not a member of the project.");
        }

        return new ProjectDto(project.getProjectId(), project.getManager().getAccountId(), project.getProjectName(), project.getProjectStatus());
    }
}


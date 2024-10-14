package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.MilestonePostDto;
import com.nhnacademy.taskapi.dto.request.MilestonePutDto;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.view.MilestoneView;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    public List<MilestoneView> getMilestones(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project with id " + projectId + " not found");
        }

        return milestoneRepository.findAllByProjectProjectId(projectId);
    }

    public void post(Long projectId, MilestonePostDto postDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException(
                "Project with id " + projectId + " not found"));
        Milestone milestone = new Milestone(postDto.progress(), project);

        milestoneRepository.save(milestone);
    }

    public MilestoneView update(Long projectId, Long milestoneId, MilestonePutDto putDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Project with id " + projectId + " not found"));
        // 가져온 프로젝트에 마일스톤이 존재하지 않으면 예외
        if (project.getMilestones().stream().noneMatch(p -> p.getMileStoneId().equals(milestoneId))) {
            throw new ResourceNotFoundException("Milestone with id " + milestoneId + " in project " + projectId + " " +
                                                        "not found");
        }

        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(
                () -> new ResourceNotFoundException("Milestone with id " + milestoneId + " not found")
        );
        milestone.setProgress(putDto.progress());

        // 마일스톤 업데이트 수행
        milestoneRepository.save(milestone);
        // view로 마일스톤 정보만 가져오기
        return milestoneRepository.findByMileStoneId(milestoneId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Milestone with id " + milestoneId + " not found"));
    }

    public void delete(Long projectId, Long milestoneId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Project with id " + projectId + " not found"));
        // 가져온 프로젝트에 마일스톤이 존재하지 않으면 예외
        if (project.getMilestones().stream().noneMatch(p -> p.getMileStoneId().equals(milestoneId))) {
            throw new ResourceNotFoundException("Milestone with id " + milestoneId + " in project " + projectId + " " +
                                                        "not found");
        }
        milestoneRepository.deleteById(milestoneId);
    }
}

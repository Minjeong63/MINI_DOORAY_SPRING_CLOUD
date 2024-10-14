package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.view.MilestoneView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    Optional<MilestoneView> findByMileStoneId(long id);

    List<MilestoneView> findAllByProjectProjectId(Long projectId);
}

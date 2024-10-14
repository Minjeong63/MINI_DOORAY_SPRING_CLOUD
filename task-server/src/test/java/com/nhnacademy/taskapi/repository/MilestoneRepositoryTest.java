package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.view.MilestoneView;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MilestoneRepositoryTest {
    @Autowired
    private MilestoneRepository milestoneRepository;

    @Test
    @Sql("project-component-test.sql")
    void findByMilestoneId() {
        Milestone milestone = milestoneRepository.findById(1L).orElse(null);

        assertNotNull(milestone);
        assertEquals(1, milestone.getProject().getProjectId());
        assertEquals("UI 설계", milestone.getProgress());
    }

    @Test
    @Sql("project-component-test.sql")
    void findByMilestoneId_notFound() {
        Milestone milestone = milestoneRepository.findById(1024L).orElse(null);
        assertNull(milestone);
    }

    @Test
    @Sql("project-component-test.sql")
    void findViewByMilestoneId() {
        MilestoneView milestone = milestoneRepository.findByMileStoneId(1L).orElse(null);

        assertNotNull(milestone);
        assertEquals("UI 설계", milestone.getProgress());
    }

    @Test
    @Sql("project-component-test.sql")
    void findViewByMilestoneId_notFound() {
        MilestoneView milestone = milestoneRepository.findByMileStoneId(1024L).orElse(null);
        assertNull(milestone);
    }

    @Test
    @Sql("project-component-test.sql")
    void findAllByProjectProjectId() {
        List<MilestoneView> milestoneViews = milestoneRepository.findAllByProjectProjectId(1L);
        List<String> milestoneNames = List.of("UI 설계", "DB 설계", "테스트");

        assertTrue(milestoneViews.stream().allMatch(m -> milestoneNames.contains(m.getProgress())));
    }
}

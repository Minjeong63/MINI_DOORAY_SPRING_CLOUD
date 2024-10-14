package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private MilestoneService milestoneService;

    @Test
    void getMilestones() {

    }
}

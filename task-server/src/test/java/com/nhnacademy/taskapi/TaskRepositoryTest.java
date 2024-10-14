package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("classpath:com/nhnacademy/taskapi/task-sample.sql")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testFindTasksByProject() {
        Project project = projectRepository.findById(1L).orElseThrow(() -> new RuntimeException("Project not found"));
        List<Task> tasks = taskRepository.findByProject(project);

        assertNotNull(tasks);
        assertEquals(3, tasks.size());

        Task firstTask = tasks.get(0);
        assertEquals("UI 설계", firstTask.getTaskContent());

        Task secondTask = tasks.get(1);
        assertEquals("DB 설계", secondTask.getTaskContent());

        Task thirdTask = tasks.get(2);
        assertEquals("테스트", thirdTask.getTaskContent());
    }
}

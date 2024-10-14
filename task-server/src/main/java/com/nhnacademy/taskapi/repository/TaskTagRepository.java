package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {
    boolean existsByTaskAndTag(Task task, Tag tag);

    Optional<TaskTag> findByTaskAndTag(Task task, Tag tag);
}

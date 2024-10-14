package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mile_stone_id", nullable = true)
    private Milestone milestone;

    @OneToMany(mappedBy = "task")
    private List<TaskTag> taskTags;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String taskContent;

    public Task() {

    }


    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

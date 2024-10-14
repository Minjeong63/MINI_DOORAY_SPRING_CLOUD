package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Setter
    private String tagName;

    public Tag(Project project, String tagName) {
        this.project = project;
        this.tagName = tagName;
    }
}

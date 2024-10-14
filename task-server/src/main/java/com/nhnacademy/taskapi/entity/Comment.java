package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;  // Primary key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;  // Many-to-one relationship with Task

    @Column(nullable = false, columnDefinition = "TEXT")  // The actual comment content
    private String commentContent;  // Comment content

    public Comment(String commentContent, Task task) {
        this.commentContent = commentContent;
        this.task = task;
    }
}

package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.view.CommentView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentView> findAllByTaskTaskId(Long taskId);
    CommentView findByCommentId(Long commentId);
}
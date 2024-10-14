package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.CommentPostDto;
import com.nhnacademy.taskapi.dto.request.CommentPutDto;
import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.view.CommentView;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.repository.CommentRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public List<CommentView> getComments(Long taskId) {
        return commentRepository.findAllByTaskTaskId(taskId);
    }

    public void postComment(Long taskId, CommentPostDto postDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " not found"));

        Comment comment = new Comment(postDto.commentContent(), task);
        commentRepository.save(comment);
    }

    public CommentView updateComment(Long taskId, Long commentId, CommentPutDto putDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));

        if (!comment.getTask().getTaskId().equals(taskId)) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found for given task");
        }

        comment.setCommentContent(putDto.commentContent());
        commentRepository.save(comment);
        return commentRepository.findByCommentId(commentId);
    }

    public void deleteComment(Long taskId, Long commentId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));

        if (!comment.getTask().getTaskId().equals(taskId)) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found for given task");
        }

        commentRepository.deleteById(commentId);
    }
}

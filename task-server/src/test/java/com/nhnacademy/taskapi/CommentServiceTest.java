package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.dto.request.CommentPostDto;
import com.nhnacademy.taskapi.dto.request.CommentPutDto;
import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.CommentRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void postCommentTest() {
        // given
        CommentPostDto postDto = new CommentPostDto( "New comment");
        //1L, "Test task"
        Task task = new Task();
        task.setTaskId(1L);
        task.setTaskContent("Test task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment("New comment", task));

        // when
        commentService.postComment(task.getTaskId(), postDto);

        // then
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void updateCommentTest() {
        // given
        CommentPutDto putDto = new CommentPutDto("Updated comment");
        Task task = new Task();
        task.setTaskId(1L);
        task.setTaskContent("Test task");
        Comment comment = new Comment("Old comment", task);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // when
        commentService.updateComment(1L, 1L, putDto);

        // then
        verify(commentRepository, times(1)).save(any(Comment.class));
        assertThat(comment.getCommentContent()).isEqualTo("Updated comment");
    }
}

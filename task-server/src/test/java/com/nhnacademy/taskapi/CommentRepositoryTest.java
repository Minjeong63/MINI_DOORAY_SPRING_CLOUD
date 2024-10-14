package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.view.CommentView;
import com.nhnacademy.taskapi.repository.CommentRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:com/nhnacademy/taskapi/repository/comment-test.sql")
@DataJpaTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void clearEntityManager() {
        entityManager.clear();
    }

    @Test
    void saveCommentTest() {
        // given: Task 1을 찾고 그 Task에 댓글을 추가
        Task task = taskRepository.findById(1L).orElseThrow();
        Comment comment = new Comment("This is a new comment", task);

        // when: Comment 저장
        Comment savedComment = commentRepository.save(comment);

        // then: Comment가 잘 저장되었는지 확인
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getCommentContent()).isEqualTo("This is a new comment");
        assertThat(savedComment.getTask().getTaskId()).isEqualTo(task.getTaskId());
    }

}

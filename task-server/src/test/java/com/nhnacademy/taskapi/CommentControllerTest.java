package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.controller.CommentController;
import com.nhnacademy.taskapi.dto.request.CommentPostDto;
import com.nhnacademy.taskapi.dto.request.CommentPutDto;
import com.nhnacademy.taskapi.entity.view.CommentView;
import com.nhnacademy.taskapi.entity.view.CommentView.TaskView;
import com.nhnacademy.taskapi.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    private CommentView commentView;
    private TaskView taskView;

    @BeforeEach
    void setUp() {
        // Mocking TaskView
        taskView = mock(CommentView.TaskView.class);
        when(taskView.getTaskId()).thenReturn(1L);

        // Mocking CommentView
        commentView = mock(CommentView.class);
        when(commentView.getCommentId()).thenReturn(1L);
        when(commentView.getCommentContent()).thenReturn("Test Comment");
        when(commentView.getTask()).thenReturn(taskView);
    }


    @Test
    void getCommentsTest() throws Exception {
        // given: 서비스 호출 시 반환될 mock 데이터 설정
        CommentView commentView = new CommentView() {
            @Override
            public Long getCommentId() {
                return 1L;
            }

            @Override
            public String getCommentContent() {
                return "Test Comment";
            }

            @Override
            public TaskView getTask() {
                return new TaskView() {
                    @Override
                    public Long getTaskId() {
                        return 1L;
                    }
                };
            }
        };

        when(commentService.getComments(ArgumentMatchers.anyLong()))
                .thenReturn(Collections.singletonList(commentView));

        // when: GET 요청을 통해 응답 검증
        ResultActions resultActions = mockMvc.perform(get("/projects/1/tasks/comments")
                .contentType(MediaType.APPLICATION_JSON));

        // then: 응답이 예상한 JSON과 일치하는지 확인
        resultActions
                .andExpect(status().isOk())  // 상태 코드가 200인지 확인
                .andExpect(jsonPath("$.code").value(200))  // 응답의 code가 200인지 확인
                .andExpect(jsonPath("$.data[0].commentId").value(1L))  // commentId가 1인지 확인
                .andExpect(jsonPath("$.data[0].commentContent").value("Test Comment"))  // commentContent가 "Test Comment"인지 확인
                .andExpect(jsonPath("$.data[0].task.taskId").value(1L));  // taskId가 1인지 확인
    }





    @Test
    void postCommentTest() throws Exception {
        // given: Mocking the service call
        CommentPostDto postDto = new CommentPostDto("New Comment");
        doNothing().when(commentService).postComment(ArgumentMatchers.anyLong(), ArgumentMatchers.any(CommentPostDto.class));

        // when: Performing POST request
        ResultActions resultActions = mockMvc.perform(post("/projects/1/tasks/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"commentContent\":\"New Comment\"}"));

        // then: Verifying the response
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201));
    }

    @Test
    void updateCommentTest() throws Exception {
        // given: Mocking the service call to return a CommentView object
        CommentPutDto putDto = new CommentPutDto("Updated Comment");
        when(commentService.updateComment(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(CommentPutDto.class)))
                .thenReturn(commentView);

        // when: Performing PUT request
        ResultActions resultActions = mockMvc.perform(put("/projects/1/tasks/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"commentContent\":\"Updated Comment\"}"));

        // then: Verifying the response
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.commentId").value(1L))
                .andExpect(jsonPath("$.data.commentContent").value("Test Comment"))
                .andExpect(jsonPath("$.data.task.taskId").value(1L));
    }

    @Test
    void deleteCommentTest() throws Exception {
        // given: Mocking the service call
        doNothing().when(commentService).deleteComment(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());

        // when: Performing DELETE request
        ResultActions resultActions = mockMvc.perform(delete("/projects/1/tasks/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON));

        // then: Verifying the response
        resultActions
                .andExpect(status().isNoContent());
    }
}

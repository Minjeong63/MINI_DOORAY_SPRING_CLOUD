package com.nhnacademy.taskapi.entity.view;

public interface CommentView {
    Long getCommentId();
    String getCommentContent();
    TaskView getTask();

    // You can add any additional fields you want to expose in the view
    interface TaskView {
        Long getTaskId();
    }
}
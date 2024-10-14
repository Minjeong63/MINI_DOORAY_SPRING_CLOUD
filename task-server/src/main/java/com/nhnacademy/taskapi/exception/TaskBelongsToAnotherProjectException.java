package com.nhnacademy.taskapi.exception;

public class TaskBelongsToAnotherProjectException extends RuntimeException {
    public TaskBelongsToAnotherProjectException(Long taskId, Long projectId) {
        super("Task with ID " + taskId + " does not belong to project with ID " + projectId + ".");
    }
}

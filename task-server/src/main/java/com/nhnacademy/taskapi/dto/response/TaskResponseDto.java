package com.nhnacademy.taskapi.dto.response;

import com.nhnacademy.taskapi.entity.Milestone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TaskResponseDto (
    Long taskId,
    Long projectId,
    Milestone milestoneId,
    String taskContent
) {
}


package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskPostDto(
        @NotNull Long projectId,
        Long milestoneId,
        @NotNull @NotBlank String taskContent
) {
}
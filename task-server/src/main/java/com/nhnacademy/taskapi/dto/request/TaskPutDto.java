package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskPutDto(
        Long milestoneId,
        @NotNull @NotBlank String taskContent
) {
}

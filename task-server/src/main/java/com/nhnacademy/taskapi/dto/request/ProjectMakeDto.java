package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectMakeDto(@NotNull @NotBlank String projectName) {
}

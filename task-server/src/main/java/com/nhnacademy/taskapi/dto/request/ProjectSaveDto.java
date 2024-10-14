package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectSaveDto(@NotNull @NotBlank String title,
                             @NotNull @NotBlank Long accountId) {
}

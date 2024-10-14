package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TagDto(@NotNull @NotBlank Long tagId, @NotNull @NotBlank String tagName) {
}

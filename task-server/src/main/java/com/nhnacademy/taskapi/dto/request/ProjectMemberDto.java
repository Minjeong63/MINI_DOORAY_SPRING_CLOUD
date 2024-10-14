package com.nhnacademy.taskapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProjectMemberDto(@NotNull Long accountId) {
}

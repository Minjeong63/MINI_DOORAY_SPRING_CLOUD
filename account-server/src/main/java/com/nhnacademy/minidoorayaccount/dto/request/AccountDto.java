package com.nhnacademy.minidoorayaccount.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

public record AccountDto(@NotNull @NotBlank String userId, @NotNull @NotBlank String password,
                         @NotNull @Email String email) {
}

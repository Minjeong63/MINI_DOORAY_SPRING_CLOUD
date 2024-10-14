package com.nhnacademy.minidoorayaccount.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.minidoorayaccount.entity.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record AccountStatusDto(@NotNull @JsonProperty("status") UserStatus status) {
}

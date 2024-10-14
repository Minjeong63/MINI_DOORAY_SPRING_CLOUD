package com.nhnacademy.taskapi.dto.request;

import com.nhnacademy.taskapi.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectDto (@NotNull @NotBlank Long projectId,  @NotNull @NotBlank Long accountId, @NotNull @NotBlank String projectName, @NotNull @NotBlank ProjectStatus projectStatus
                          ) {

}

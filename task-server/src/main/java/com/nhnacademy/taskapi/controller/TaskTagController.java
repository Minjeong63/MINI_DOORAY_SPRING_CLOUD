package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.request.TagDto;
import com.nhnacademy.taskapi.dto.request.TagPostDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/tags/{tagId}")
public class TaskTagController {

    private final TagService tagService;

    public TaskTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<DefaultDto> saveTaskTag(@PathVariable Long projectId,
                                                  @PathVariable Long taskId,
                                                  @PathVariable Long tagId
    ) {
        tagService.saveTaskTag(taskId, tagId);
        DefaultDto dto = new DefaultDto(201, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping
    public ResponseEntity<DefaultDto> deleteTaskTag(@PathVariable Long projectId,
                                                    @PathVariable Long taskId,
                                                    @PathVariable Long tagId
    ) {
        tagService.deleteTaskTag(taskId,tagId);
        DefaultDto dto = new DefaultDto(204, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }
}

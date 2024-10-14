package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.request.TagDto;
import com.nhnacademy.taskapi.dto.request.TagPostDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tags")
public class TagController {


    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    //태그 등록
    @PostMapping
    public ResponseEntity<DefaultDto<Object>> saveTag(@RequestBody TagPostDto tagDto,
                                              @PathVariable Long projectId
    ) {
        tagService.saveTag(projectId, tagDto);
        DefaultDto<Object> dto = new DefaultDto<>(201, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //태그 조회
    @GetMapping
    public ResponseEntity<DefaultDto<List<TagDto>>> getAllTags(@PathVariable("projectId") Long projectId) {
        List<TagDto> tags = tagService.getTagsByProjectId(projectId);
        DefaultDto<List<TagDto>> defaultDto = new DefaultDto<>(200, tags);
        return ResponseEntity.status(HttpStatus.CREATED).body(defaultDto);
    }

    //태그 수정
    @PutMapping
    public ResponseEntity<DefaultDto<TagDto>> updateTag(@PathVariable Long projectId,
                                                @RequestBody TagDto tagDto
    ) {
        TagDto updateTagDto = tagService.updateTag(projectId, tagDto);
        DefaultDto<TagDto> dto = new DefaultDto<>(200, updateTagDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //태그 삭제
    @DeleteMapping
    public ResponseEntity<DefaultDto<Object>> deleteProject(@PathVariable Long projectId,
                                                    @RequestBody TagDto tagDto
    ) {
        tagService.deleteTag(projectId, tagDto);
        DefaultDto<Object> dto = new DefaultDto<>(200, null);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}


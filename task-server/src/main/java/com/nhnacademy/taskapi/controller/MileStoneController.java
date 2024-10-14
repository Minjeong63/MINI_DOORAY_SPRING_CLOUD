package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.request.MilestonePostDto;
import com.nhnacademy.taskapi.dto.request.MilestonePutDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.entity.view.MilestoneView;
import com.nhnacademy.taskapi.service.MilestoneService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/")
@RequiredArgsConstructor
public class MileStoneController {
    private final MilestoneService milestoneService;

    @GetMapping("/{projectId}/milestones")
    public ResponseEntity<DefaultDto<List<MilestoneView>>> getMilestones(@PathVariable("projectId") Long projectId) {
        List<MilestoneView> milestones = milestoneService.getMilestones(projectId);
        DefaultDto<List<MilestoneView>> dto = new DefaultDto<>(200, milestones);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{projectId}/milestones")
    public ResponseEntity<DefaultDto<Object>> postMilestone(@PathVariable("projectId") Long projectId,
                                                    @RequestBody MilestonePostDto postDto) {
        milestoneService.post(projectId, postDto);
        DefaultDto<Object> dto = new DefaultDto<>(201, null);

        return ResponseEntity.status(201).body(dto);
    }

    @PutMapping("/{projectId}/milestones/{milestoneId}")
    public ResponseEntity<DefaultDto<MilestoneView>> updateMilestone(@PathVariable Long projectId, @PathVariable Long milestoneId,
                                                      @RequestBody MilestonePutDto putDto) {
        MilestoneView milestoneView = milestoneService.update(projectId, milestoneId, putDto);
        return ResponseEntity.ok(new DefaultDto<>(200, milestoneView));
    }

    @DeleteMapping("/{projectId}/milestones/{milestoneId}")
    public ResponseEntity<DefaultDto<Object>> deleteMilestone(@PathVariable Long projectId, @PathVariable Long milestoneId) {
        milestoneService.delete(projectId, milestoneId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new DefaultDto<>(HttpServletResponse
                                       .SC_NO_CONTENT, null));
    }
}

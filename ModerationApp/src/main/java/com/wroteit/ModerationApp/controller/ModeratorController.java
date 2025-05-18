package com.wroteit.ModerationApp.controller;

import com.wroteit.ModerationApp.model.Report;
import com.wroteit.ModerationApp.service.ModeratorService;
import com.wroteit.ModerationApp.dto.AssignRequest;
import com.wroteit.ModerationApp.dto.BanRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderators")
public class ModeratorController {

    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @PostMapping("/reports")
    public Report fileReport(@RequestBody Report report) {
        return moderatorService.fileReport(report);
    }

    @GetMapping("/reports/status/{status}")
    public List<Report> getReportsByStatus(@PathVariable String status) {
        return moderatorService.getReportsByStatus(status);
    }

    @PutMapping("/reports/{id}/review")
    public Report reviewReport(@PathVariable Long id, @RequestBody String newStatus) {
        return moderatorService.reviewReport(id, newStatus);
    }

    @DeleteMapping("/reports/{id}")
    public void deleteReport(@PathVariable Long id) {
        moderatorService.deleteReport(id);
    }

    @PostMapping("/assign")
    public String assignModerator(@RequestBody AssignRequest request) {
        return moderatorService.assignModerator(request.getUserId(), request.getCommunityId());
    }

    @PostMapping("/ban")
    public String banUser(@RequestBody BanRequest request) {
        return moderatorService.banUser(request.getUserId(), request.getCommunityId());
    }

    @DeleteMapping("/content/{entityType}/{postId}")
    public String deleteContent(@PathVariable String entityType, @PathVariable Long postId) {
        return moderatorService.deleteContent(entityType, postId);
    }
}

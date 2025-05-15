package main.java.com.wroteit.ModerationApp.controller;

import com.wroteit.ModerationApp.model.Report;
import com.wroteit.ModerationApp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderators/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> fileReport(@RequestBody Report report) {
        return ResponseEntity.ok(reportService.fileReport(report));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Report>> getReportsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(reportService.getReportsByStatus(status));
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<Report> reviewReport(@PathVariable Long id, @RequestBody String status) {
        Report updated = reportService.reviewReport(id, status);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        boolean deleted = reportService.deleteReport(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignModerator(@RequestBody AssignRequest request) {
        String result = reportService.assignModerator(request.getUserId(), request.getCommunityId());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ban")
    public ResponseEntity<String> banUser(@RequestBody BanRequest request) {
        String result = reportService.banUser(request.getUserId(), request.getCommunityId());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/content/{entityType}/{postId}")
    public ResponseEntity<String> deleteContent(@PathVariable String entityType, @PathVariable Long postId) {
        String result = reportService.deleteContent(entityType, postId);
        return ResponseEntity.ok(result);
    }
}

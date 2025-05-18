// package com.wroteit.ModerationApp.controller;

// import com.wroteit.ModerationApp.model.Report;
// import com.wroteit.ModerationApp.service.ReportService;
// import com.wroteit.ModerationApp.dto.AssignRequest;
// import com.wroteit.ModerationApp.dto.BanRequest;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/moderators/reports")
// public class ReportController {

//     private final ReportService reportService;

//     public ReportController(ReportService reportService) {
//         this.reportService = reportService;
//     }

//     @PostMapping("/{creatorId}")
//     public Report fileReport(@PathVariable Long creatorId, @RequestBody Report report) {
//         return reportService.fileReport(report);
//     }

//     @GetMapping("/status/{status}")
//     public List<Report> getReportsByStatus(@PathVariable String status) {
//         return reportService.getReportsByStatus(status);
//     }

//     @PutMapping("/{reportId}/review")
//     public Report reviewReport(@PathVariable Long reportId, @RequestBody String newStatus) {
//         return reportService.reviewReport(reportId, newStatus);
//     }

//     @DeleteMapping("/{reportId}")
//     public void deleteReport(@PathVariable Long reportId) {
//         reportService.deleteReport(reportId);
//     }

//     @PostMapping("/assign")
//     public String assignModerator(@RequestBody AssignRequest request) {
//         return reportService.assignModerator(request.getUserId(), request.getCommunityId());
//     }

//     @PostMapping("/ban")
//     public String banUser(@RequestBody BanRequest request) {
//         return reportService.banUser(request.getUserId(), request.getCommunityId());
//     }

//     @DeleteMapping("/content/{entityType}/{entityId}")
//     public String deleteContent(@PathVariable String entityType, @PathVariable Long entityId) {
//         return reportService.deleteContent(entityType, entityId);
//     }
// }

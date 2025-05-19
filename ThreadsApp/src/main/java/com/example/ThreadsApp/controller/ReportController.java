package com.example.ThreadsApp.controller;

import com.example.ThreadsApp.model.Report;
import com.example.ThreadsApp.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<?> getAllReports() {
        List<Report> reports = reportService.findAll();
        return ResponseEntity.ok(reports);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable String id) {
        Optional<Report> report = reportService.findById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> submitReport(@RequestBody Report report) {
        Optional<Report> existing = reportService.findDuplicate(
                report.getReporterId(), report.getTargetType(), report.getTargetId());

        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("You have already reported this item.");
        }

        report.setReportedAt(LocalDateTime.now());
        reportService.saveReport(report);
        return ResponseEntity.ok("Report submitted successfully.");
    }
}

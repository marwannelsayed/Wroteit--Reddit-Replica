package com.example.ThreadsApp.controller;

import com.example.ThreadsApp.model.Report;
import com.example.ThreadsApp.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepo;

    @Autowired
    public ReportController(ReportRepository reportRepo) {
        this.reportRepo = reportRepo;
    }

    @PostMapping
    public ResponseEntity<?> submitReport(@RequestBody Report report) {
        Optional<Report> existing = reportRepo.findByReporterIdAndTargetTypeAndTargetId(
                report.getReporterId(), report.getTargetType(), report.getTargetId());

        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("You have already reported this item.");
        }

        report.setReportedAt(LocalDateTime.now());
        reportRepo.save(report);
        return ResponseEntity.ok("Report submitted successfully.");
    }
}

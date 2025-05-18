package com.example.ThreadsApp.service;

import com.example.ThreadsApp.model.Report;
import com.example.ThreadsApp.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepo;

    public ReportService(ReportRepository reportRepo) {
        this.reportRepo = reportRepo;
    }

    public Optional<Report> findById(String id) {
        return reportRepo.findById(id);
    }

    public List<Report> findAll() {
        return reportRepo.findAll();
    }

    public Optional<Report> findDuplicate(String reporterId, String targetType, String targetId) {
        return reportRepo.findByReporterIdAndTargetTypeAndTargetId(reporterId, targetType, targetId);
    }

    public Report saveReport(Report report) {
        report.setReportedAt(LocalDateTime.now());
        return reportRepo.save(report);
    }
}

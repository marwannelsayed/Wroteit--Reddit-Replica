package com.example.threadapp.service;

import com.example.threadapp.model.Report;
import com.example.threadapp.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepo;

    public Optional<Report> findDuplicate(String reporterId, String targetType, String targetId) {
        return reportRepo.findByReporterIdAndTargetTypeAndTargetId(reporterId, targetType, targetId);
    }

    public Report saveReport(Report report) {
        report.setReportedAt(LocalDateTime.now());
        return reportRepo.save(report);
    }
}

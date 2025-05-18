package com.wroteit.ModerationApp.service;

import com.wroteit.ModerationApp.model.Report;
import com.wroteit.ModerationApp.repository.ReportRepository;
import com.wroteit.ModerationApp.repository.ModeratorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ModeratorService {

    private final ReportRepository reportRepository;
    private final ModeratorRepository moderatorRepository;

    public ModeratorService(ReportRepository reportRepository, ModeratorRepository moderatorRepository) {
        this.reportRepository = reportRepository;
        this.moderatorRepository = moderatorRepository;
    }

    public Report fileReport(Report report) {
        report.setStatus("pending");
        report.setTimestamp(LocalDateTime.now());
        return reportRepository.save(report);
    }

    public Report reviewReport(Long reportId, String newStatus) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (optionalReport.isEmpty()) {
            return null;
        }
        Report report = optionalReport.get();
        report.setStatus(newStatus);
        return reportRepository.save(report);
    }

    public List<Report> getReportsByStatus(String status) {
        return reportRepository.findByStatus(status);
    }

    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }

    public String assignModerator(Long userId, Long communityId) {
        // Your logic to assign a moderator
        return "Moderator " + userId + " assigned to community " + communityId;
    }

    public String banUser(Long userId, Long communityId) {
        // Your logic to ban user
        return "User " + userId + " banned from community " + communityId;
    }

    public String deleteContent(String entityType, Long entityId) {
        return "Deleted " + entityType + " with ID " + entityId;
    }

    public String moderateReport(Long reportId, Long moderatorId, String action) {
        return "Moderator " + moderatorId + " performed action '" + action + "' on report " + reportId;
    }
}

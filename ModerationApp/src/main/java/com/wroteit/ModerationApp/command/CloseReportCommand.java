package com.wroteit.ModerationApp.command;

import com.wroteit.ModerationApp.model.Moderator;
import com.wroteit.ModerationApp.repository.ModeratorRepository;
import com.wroteit.ModerationApp.repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CloseReportCommand implements ModerationCommand {
    private final ReportRepository reportRepository;
    private final Long reportId;

    public CloseReportCommand(ReportRepository reportRepository, Long reportId) {
        this.reportRepository = reportRepository;
        this.reportId = reportId;
    }

    @Override
    public void execute() {

        reportRepository.deleteById(reportId);

    }
}
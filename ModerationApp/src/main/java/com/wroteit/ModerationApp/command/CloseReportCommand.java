package com.wroteit.ModerationApp.command;


import com.wroteit.ModerationApp.repository.ReportRepository;


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
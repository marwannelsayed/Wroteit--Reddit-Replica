package com.wroteit.ModerationApp.service;

import com.wroteit.ModerationApp.command.AssignModeratorCommand;
import com.wroteit.ModerationApp.command.CloseReportCommand;
import com.wroteit.ModerationApp.model.Moderator;
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
        return reportRepository.save(report);
    }

    public List<Report> findByCommunityId(Long communityId){
        return reportRepository.findByCommunityId(communityId);
    }




    public String assignModerator(Long userId, Long communityId) {
        AssignModeratorCommand assignModeratorCommand = new AssignModeratorCommand(moderatorRepository, userId, communityId);
        try {
            assignModeratorCommand.execute();
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Moderator added to community successfully";
    }



    public String closeReport(Long reportId) {
        CloseReportCommand closeReportCommand = new CloseReportCommand(reportRepository, reportId);
        return "Report closed successfully";
    }
}

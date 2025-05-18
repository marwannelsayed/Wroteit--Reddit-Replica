// package main.java.com.wroteit.ModerationApp.service;

// import com.wroteit.ModerationApp.model.Report;
// import com.wroteit.ModerationApp.repository.ReportRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class ReportService {

//     @Autowired
//     private ReportRepository reportRepository;

//     public Report fileReport(Report report) {
//         report.setStatus("pending");
//         report.setTimestamp(LocalDateTime.now());
//         return reportRepository.save(report);
//     }

//     public Report reviewReport(Long reportId, String newStatus) {
//         Optional<Report> optionalReport = reportRepository.findById(reportId);
//         if (optionalReport.isEmpty()) return null;

//         Report report = optionalReport.get();
//         report.setStatus(newStatus);
//         return reportRepository.save(report);
//     }

//     public List<Report> getReportsByStatus(String status) {
//         return reportRepository.findByStatus(status);
//     }

//     public boolean deleteReport(Long reportId) {
//         if (!reportRepository.existsById(reportId)) return false;
//         reportRepository.deleteById(reportId);
//         return true;
//     }

//     public String assignModerator(Long userId, Long communityId) {
//         return "Moderator " + userId + " assigned to community " + communityId;
//     }

//     public String banUser(Long userId, Long communityId) {
//         return "User " + userId + " banned from community " + communityId;
//     }

//     public String deleteContent(String entityType, Long entityId) {
//         return "Deleted " + entityType + " with ID: " + entityId;
//     }

//     public String moderateReport(Long reportId, Long moderatorId, String action) {
//         return "Moderator " + moderatorId + " performed action '" + action + "' on report " + reportId;
//     }
// }

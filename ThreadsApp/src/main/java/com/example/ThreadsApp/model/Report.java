package com.example.ThreadsApp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "reports")
public class Report {

    @Id
    private String id;
    private String reporterId;        // User who reported
    private String targetId;          // ID of the thread or comment being reported
    private String targetType;        // "thread" or "comment"
    private String reason;            // Reason for reporting (e.g., spam, abuse, etc.)
    private LocalDateTime reportedAt; // Timestamp of the report

    public Report() {
        this.reportedAt = LocalDateTime.now();
    }

    public Report(String reporterId, String targetId, String targetType, String reason) {
        this.reporterId = reporterId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.reason = reason;
        this.reportedAt = LocalDateTime.now();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getReporterId() {
        return reporterId;
    }
    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }
    public String getTargetId() {
        return targetId;
    }
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    public String getTargetType() {
        return targetType;
    }
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public LocalDateTime getReportedAt() {
        return reportedAt;
    }
    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }
}

package com.wroteit.ModerationApp.model;

import java.time.LocalDateTime;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long communityId;
    private Long reporterId;
    private Long reportedEntityId;
    private String entityType;
    private String reason;
    private LocalDateTime timestamp;

    public Report() {}

    public Report(Long reporterId, Long reportedEntityId, String entityType, String reason, LocalDateTime timestamp) {
        this.reporterId = reporterId;
        this.reportedEntityId = reportedEntityId;
        this.entityType = entityType;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public Long getReportedEntityId() {
        return reportedEntityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getReason() {
        return reason;
    }



    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public void setReportedEntityId(Long reportedEntityId) {
        this.reportedEntityId = reportedEntityId;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

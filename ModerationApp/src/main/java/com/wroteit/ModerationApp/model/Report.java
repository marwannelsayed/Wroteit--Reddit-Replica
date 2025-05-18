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

    private Long reporterId;
    private Long reportedEntityId;
    private String entityType;
    private String reason;
    private String status;
    private LocalDateTime timestamp;

    public Report() {}

    public Report(Long reporterId, Long reportedEntityId, String entityType, String reason, String status, LocalDateTime timestamp) {
        this.reporterId = reporterId;
        this.reportedEntityId = reportedEntityId;
        this.entityType = entityType;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters and setters omitted for brevity
}

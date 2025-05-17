package com.example.threadapp.model;

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
}

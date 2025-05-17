package com.wroteit.NotificationApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private String recipientId;
    private String message;
    private NotificationType type;
    private boolean isRead;                 // Changed from enum to boolean
    private LocalDateTime createdAt;

    public enum NotificationType {
        COMMENT,
        MENTION,
        LIKE,
        FOLLOW,
        MESSAGE,
        SYSTEM
    }

    public Notification() {}

    public Notification(String recipientId, String type, String message) {
        this.recipientId = recipientId;
        this.type = NotificationType.valueOf(type);
        this.message = message;
        this.isRead = false;               // Initialize as unread
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { this.isRead = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}


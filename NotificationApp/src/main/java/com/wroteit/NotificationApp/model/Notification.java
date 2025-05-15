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
    private NotificationStatus status;
    private LocalDateTime timestamp;

    public enum NotificationType {
        COMMENT,
        MENTION,
        LIKE,
        FOLLOW,
        MESSAGE,
        SYSTEM
    }

    public enum NotificationStatus {
        READ,
        UNREAD
    }

    // Constructors
    public Notification() {}

    public Notification(String recipientId, String message, NotificationType type, NotificationStatus status, LocalDateTime timestamp) {
        this.recipientId = recipientId;
        this.message = message;
        this.type = type;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}


//package com.wroteit.notification.model;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.time.LocalDateTime;
//
//@Document(collection = "notifications")
//public class Notification {
//
//    @Id
//    private String id;
//    private String recipientId;
//    private String type; // e.g., reply, ban, report
//    private String message;
//    private LocalDateTime timestamp;
//
//    public Notification() {
//        this.timestamp = LocalDateTime.now();
//    }
//
//    public Notification(String recipientId, String type, String message) {
//        this.recipientId = recipientId;
//        this.type = type;
//        this.message = message;
//        this.timestamp = LocalDateTime.now();
//    }
//
//    // Getters and Setters
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getRecipientId() {
//        return recipientId;
//    }
//
//    public void setRecipientId(String recipientId) {
//        this.recipientId = recipientId;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(LocalDateTime timestamp) {
//        this.timestamp = timestamp;
//    }
//}

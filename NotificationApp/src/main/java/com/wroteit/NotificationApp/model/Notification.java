package com.wroteit.NotificationApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private Long recipientId;
    private String message;
    private NotificationType type;
    private boolean isRead;                 // Changed from enum to boolean
    private LocalDateTime createdAt;
    private List<DeliveryMethod> deliveryMethods;


    public enum NotificationType {
        COMMENT_REPLY,
        THREAD_REPLY,
        BAN,
        SUBSCRIPTION,
        REPORT
    }

    public enum DeliveryMethod {
        IN_APP, EMAIL, MOBILE_BANNER
    }

    public Notification() {}

    public Notification(Long recipientId, NotificationType type, String message) {
        this.recipientId = recipientId;
        this.type = type;
        this.message = message;
    }

    // Getters and setters

    public String getId() { return id; }

    public Long getRecipientId() { return recipientId; }
    public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { this.isRead = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(List<DeliveryMethod> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }
}


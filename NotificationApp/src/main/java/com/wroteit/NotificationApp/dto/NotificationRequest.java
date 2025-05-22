package com.wroteit.NotificationApp.dto;

import com.wroteit.NotificationApp.model.Notification;
import java.util.List;

public class NotificationRequest {
    private Long recipientId;
    private String message;

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
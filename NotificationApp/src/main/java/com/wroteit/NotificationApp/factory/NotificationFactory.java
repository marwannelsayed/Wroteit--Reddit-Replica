package com.wroteit.NotificationApp.factory;

import com.wroteit.NotificationApp.model.Notification;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class NotificationFactory {

    public Notification createNotification(String recipientId, String message, Notification.NotificationType type) {
        return Notification.builder()
                .recipientId(recipientId)
                .message(message)
                .type(type)
                .status(Notification.NotificationStatus.UNREAD)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
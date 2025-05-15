package com.wroteit.NotificationApp.factory;

import com.wroteit.NotificationApp.model.Notification;

import java.time.LocalDateTime;

public class NotificationFactory {

    public static Notification createNotification(String recipientId, String message, Notification.NotificationType type) {
        return new Notification(
                recipientId,
                message,
                type,
                Notification.NotificationStatus.UNREAD,
                LocalDateTime.now()
        );
    }
}

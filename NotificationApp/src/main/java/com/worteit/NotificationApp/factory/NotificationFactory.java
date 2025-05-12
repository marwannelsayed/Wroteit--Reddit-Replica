package com.wroteit.notification.factory;

import com.wroteit.notification.model.Notification;

public class NotificationFactory {
    public static Notification create(String type, String recipientId, String message) {
        return new Notification(recipientId, type, message);
    }
}

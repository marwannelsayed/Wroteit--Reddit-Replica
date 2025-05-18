package com.wroteit.NotificationApp.Strategy;

import com.wroteit.NotificationApp.model.Notification;

public class EmailNotification implements NotificationStrategy {
    public void send(Notification notification) {
        System.out.println("Email notification:\nCheck the app to see your new " + notification.getType() + "!\nContent: " + notification.getMessage());
    }
}

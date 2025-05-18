package com.wroteit.NotificationApp.Strategy;

import com.wroteit.NotificationApp.model.Notification;

public class MobileBannerNotification implements NotificationStrategy {
    public void send(Notification notification) {
        System.out.println("Mobile banner notification:\nYou just got a new " + notification.getType() + "!\nContent: " + notification.getMessage());
    }
}

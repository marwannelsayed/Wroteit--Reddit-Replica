package com.wroteit.NotificationApp.Strategy;

import com.wroteit.NotificationApp.model.Notification;

import java.util.List;

public interface NotificationStrategy {
    void send(Notification notification);
}


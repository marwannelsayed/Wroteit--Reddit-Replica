package com.wroteit.NotificationApp.Strategy;

import com.wroteit.NotificationApp.model.Notification;

import java.util.List;

public class NotificationContext {
    private final List<NotificationStrategy> strategies;

    public NotificationContext(List<NotificationStrategy> strategies) {
        this.strategies = strategies;
    }

    public void notify(Notification notification) {
        for (NotificationStrategy strategy : strategies) {
            strategy.send(notification);
        }
    }
}

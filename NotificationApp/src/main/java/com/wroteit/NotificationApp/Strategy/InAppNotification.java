package com.wroteit.NotificationApp.Strategy;

import com.wroteit.NotificationApp.model.Notification;
import com.wroteit.NotificationApp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InAppNotification implements NotificationStrategy {
    private final NotificationRepository notificationRepository;


    @Autowired
    public InAppNotification(
            NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void send(Notification notification) {
        notificationRepository.save(notification);
    }
}

package com.wroteit.notification.service;

import com.wroteit.notification.model.Notification;
import com.wroteit.notification.repository.NotificationRepository;
import com.wroteit.notification.factory.NotificationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification sendNotification(String recipientId, String type, String message) {
        Notification notification = NotificationFactory.create(type, recipientId, message);
        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findByRecipientId(userId);
    }
}

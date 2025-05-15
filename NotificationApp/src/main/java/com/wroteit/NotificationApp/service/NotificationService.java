package com.wroteit.NotificationApp.service;

import com.wroteit.NotificationApp.factory.NotificationFactory;
import com.wroteit.NotificationApp.model.Notification;
import com.wroteit.NotificationApp.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification sendNotification(String recipientId, String message, Notification.NotificationType type) {
        Notification notification = NotificationFactory.createNotification(recipientId, message, type);
        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findByRecipientId(userId);
    }
}


//package com.wroteit.notification.service;
//
//import com.wroteit.notification.model.Notification;
//import com.wroteit.notification.repository.NotificationRepository;
//import com.wroteit.notification.factory.NotificationFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class NotificationService {
//
//    @Autowired
//    private NotificationRepository notificationRepository;
//
//    public Notification sendNotification(String recipientId, String type, String message) {
//        Notification notification = NotificationFactory.create(type, recipientId, message);
//        return notificationRepository.save(notification);
//    }
//
//    public List<Notification> getUserNotifications(String userId) {
//        return notificationRepository.findByRecipientId(userId);
//    }
//}

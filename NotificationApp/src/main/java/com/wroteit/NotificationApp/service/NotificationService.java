
package com.wroteit.NotificationApp.service;


import com.wroteit.NotificationApp.Strategy.*;
import com.wroteit.NotificationApp.factory.NotificationFactory;
import com.wroteit.NotificationApp.model.Notification;
import com.wroteit.NotificationApp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;


    @Autowired
    public NotificationService(
            NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Send notif
    public Notification sendNotification(Long recipientId, String message, Notification.NotificationType type, List<Notification.DeliveryMethod> deliveryMethods) {
        Notification notification = NotificationFactory.createNotification(recipientId, message, type, deliveryMethods);
        List<NotificationStrategy> strategies = new ArrayList<>();
        if(notification.getDeliveryMethods().contains(Notification.DeliveryMethod.IN_APP)){
            strategies.add(new InAppNotification(notificationRepository));
        }
        if(notification.getDeliveryMethods().contains(Notification.DeliveryMethod.MOBILE_BANNER)){
            strategies.add(new MobileBannerNotification());
        }
        if(notification.getDeliveryMethods().contains(Notification.DeliveryMethod.EMAIL)){
            strategies.add(new EmailNotification());
        }
        NotificationContext notificationContext = new NotificationContext(strategies);
        notificationContext.notify(notification);
        return notification;
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByRecipientId(userId);
    }

    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByRecipientIdAndIsReadFalse(userId);
    }

    // Mark notification as read
    public Notification markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if(notification!=null){
            notification.setRead(true);
        }
        return notification;
    }

    // Delete notif
    public String deleteNotification(String notificationId) {
        if(notificationRepository.existsById(notificationId)){
            notificationRepository.deleteById(notificationId);
            return "Notification deleted successfully!";
        }else{
            return "Notification not found.";
        }
    }
}

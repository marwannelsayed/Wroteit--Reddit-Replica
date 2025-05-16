package com.wroteit.UserApp.NotificationApp.controller;
import com.wroteit.UserApp.NotificationApp.model.Notification;
import com.wroteit.UserApp.NotificationApp.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/unread/{userId}")
    public List<Notification> getUnreadNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return notifications;
    }

    @PutMapping("/{id}/read")
    public Notification markNotificationAsRead(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        return notification;
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
}
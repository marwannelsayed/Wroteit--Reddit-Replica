package com.wroteit.notification.controller;

import com.wroteit.notification.model.Notification;
import com.wroteit.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public Notification createNotification(
            @RequestParam String recipientId,
            @RequestParam String type,
            @RequestParam String message) {
        return notificationService.sendNotification(recipientId, type, message);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable String userId) {
        return notificationService.getUserNotifications(userId);
    }
}

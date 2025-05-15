package com.wroteit.NotificationApp.controller;

import com.wroteit.NotificationApp.model.Notification;
import com.wroteit.NotificationApp.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    // TODO: fel integration with Userapp, validate recipientId when creating notifications,
    // TODO: Plan integration for receiving notification events from ModeratorApp, ThreadsApp, and CommunitiesApp
    private final NotificationService notificationService;

//    @GetMapping("/test")
//    public String test() {
//        return "Notification service works";
//    }

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Map<String, Object> request) {
        try {
            String recipientId = (String) request.get("recipientId");
            String message = (String) request.get("message");
            Notification.NotificationType type = Notification.NotificationType.valueOf((String) request.get("type"));

            Notification notification = notificationService.sendNotification(recipientId, message, type);
            return new ResponseEntity<>(notification, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(notifications);
    }
}


//package com.wroteit.NotificationApp.controller;
//
//import com.wroteit.NotificationApp.model.Notification;
//import com.wroteit.NotificationApp.service.NotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/notifications")
//public class NotificationController {
//
//    private final NotificationService notificationService;
//
//    @Autowired
//    public NotificationController(NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }
//
//    @PostMapping
//    public ResponseEntity<Notification> createNotification(@RequestBody Map<String, Object> request) {
//        String recipientId = (String) request.get("recipientId");
//        String message = (String) request.get("message");
//        Notification.NotificationType type = Notification.NotificationType.valueOf((String) request.get("type"));
//
//        Notification notification = notificationService.sendNotification(recipientId, message, type);
//        return new ResponseEntity<>(notification, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
//        List<Notification> notifications = notificationService.getUserNotifications(userId);
//        return ResponseEntity.ok(notifications);
//    }
//}


//package com.wroteit.notification.controller;
//
//import com.wroteit.notification.model.Notification;
//import com.wroteit.notification.service.NotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/notifications")
//public class NotificationController {
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @PostMapping
//    public Notification createNotification(
//            @RequestParam String recipientId,
//            @RequestParam String type,
//            @RequestParam String message) {
//        return notificationService.sendNotification(recipientId, type, message);
//    }
//
//    @GetMapping("/user/{userId}")
//    public List<Notification> getUserNotifications(@PathVariable String userId) {
//        return notificationService.getUserNotifications(userId);
//    }
//}

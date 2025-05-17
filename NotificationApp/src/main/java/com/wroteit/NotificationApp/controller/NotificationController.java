
package com.wroteit.NotificationApp.controller;

import com.wroteit.NotificationApp.model.Notification;
import com.wroteit.NotificationApp.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")

// TODO: fel integration with Userapp, validate recipientId when creating notifications,
// TODO: Plan integration for receiving notification events from ModeratorApp, ThreadsApp, and CommunitiesApp

public class NotificationController {

    private final NotificationService notificationService;

//    @GetMapping("/ping")
//    public String ping() {
//        return "Notification service is up!";
//    }

    @Autowired
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

    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable String userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable String id) {
        Notification notification = notificationService.markAsRead(id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable String id) {
        boolean deleted = notificationService.deleteNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Notification deleted.");
        }
        return new ResponseEntity<>("Notification not found.", HttpStatus.NOT_FOUND);
    }
}

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
//    // Alaa: Create a notification
//    @PostMapping
//    public ResponseEntity<Notification> createNotification(@RequestBody Map<String, Object> request) {
//        try {
//            String recipientId = (String) request.get("recipientId");
//            String message = (String) request.get("message");
//            Notification.NotificationType type = Notification.NotificationType.valueOf((String) request.get("type"));
//
//            Notification notification = notificationService.sendNotification(recipientId, message, type);
//            return new ResponseEntity<>(notification, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // Alaa: Get all notifications for a user
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
//        List<Notification> notifications = notificationService.getUserNotifications(userId);
//        if (notifications.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(notifications);
//    }
//
//    // Mohamed: Get unread notifications for a user
//    @GetMapping("/unread/{userId}")
//    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
//        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
//        if (notifications.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(notifications);
//    }
//
//    // Mohamed: Mark a notification as read
//    @PutMapping("/{id}/read")
//    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable Long id) {
//        Notification notification = notificationService.markAsRead(id);
//        if (notification != null) {
//            return ResponseEntity.ok(notification);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    // Mohamed: Delete a notification
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
//        boolean deleted = notificationService.deleteNotification(id);
//        if (deleted) {
//            return ResponseEntity.ok("Notification deleted.");
//        }
//        return new ResponseEntity<>("Notification not found.", HttpStatus.NOT_FOUND);
//    }
//}

//package com.wroteit.NotificationApp.controller;
//
//import com.wroteit.NotificationApp.model.Notification;
//import com.wroteit.NotificationApp.service.NotificationService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/notifications")
//public class NotificationController {
//
//    // TODO: fel integration with Userapp, validate recipientId when creating notifications,
//    // TODO: Plan integration for receiving notification events from ModeratorApp, ThreadsApp, and CommunitiesApp
//    private final NotificationService notificationService;
//
////    @GetMapping("/test")
////    public String test() {
////        return "Notification service works";
////    }
//
//    public NotificationController(NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }
//
//    @PostMapping
//    public ResponseEntity<Notification> createNotification(@RequestBody Map<String, Object> request) {
//        try {
//            String recipientId = (String) request.get("recipientId");
//            String message = (String) request.get("message");
//            Notification.NotificationType type = Notification.NotificationType.valueOf((String) request.get("type"));
//
//            Notification notification = notificationService.sendNotification(recipientId, message, type);
//            return new ResponseEntity<>(notification, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
//        List<Notification> notifications = notificationService.getUserNotifications(userId);
//        if (notifications.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(notifications);
//    }
//}


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

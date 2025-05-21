package com.wroteit.NotificationApp.controller;

import com.wroteit.NotificationApp.model.Notification;
import com.wroteit.NotificationApp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")

// TODO: Plan integration for receiving notification events from ModeratorApp, ThreadsApp, and CommunitiesApp


public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/comment")
    public Notification createPostCommentNotification(@RequestBody Long recipientId, @RequestBody String message) { // Call to parent when commenting on post
        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.THREAD_REPLY, List.of(Notification.DeliveryMethod.MOBILE_BANNER));
    }

    @PostMapping("/reply")
    public Notification createCommentReplyNotification(@RequestBody Long recipientId, @RequestBody String message) { // Call to parent when commenting on comment
        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.COMMENT_REPLY, List.of(Notification.DeliveryMethod.MOBILE_BANNER));
    }

    @PostMapping("/ban")
    public Notification createBanNotification(@RequestBody Long recipientId, @RequestBody String message) { // Call to user when banned via ModerationApp
        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.BAN, List.of(Notification.DeliveryMethod.EMAIL, Notification.DeliveryMethod.IN_APP));
    }

    @PostMapping("/subscribe")
    public Notification createSubscriptionNotification(@RequestBody Long recipientId, @RequestBody String message) { // Call to each moderator of community when user subscribes
        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.SUBSCRIPTION, List.of(Notification.DeliveryMethod.EMAIL, Notification.DeliveryMethod.MOBILE_BANNER));
    }

    @PostMapping("/report")
    public Notification createReportNotification(@RequestBody Long recipientId, @RequestBody String message) { // Call to each moderator of community when thread is reported in their community
        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.REPORT, List.of(Notification.DeliveryMethod.EMAIL, Notification.DeliveryMethod.IN_APP));
    }



    @GetMapping("/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/unread/{userId}")
    public List<Notification> getUnreadNotifications(@PathVariable Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    @PutMapping("/{id}/read")
    public Notification markNotificationAsRead(@PathVariable String id) {
        return notificationService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable String id) {
        return notificationService.deleteNotification(id);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "NotificationApp is running!";
    }

    @PostMapping("/dummy")
    public Notification createDummyNotification() {
        Long dummyRecipientId = 999L;
        String dummyMessage = "This is a dummy notification for testing";
        List<Notification.DeliveryMethod> dummyDeliveryMethods = List.of(Notification.DeliveryMethod.IN_APP);

        return notificationService.sendNotification(dummyRecipientId, dummyMessage, Notification.NotificationType.BAN, dummyDeliveryMethods);
    }



}

//package com.wroteit.NotificationApp.controller;
//
//import com.wroteit.NotificationApp.model.Notification;
//import com.wroteit.NotificationApp.service.NotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
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
//    private List<Notification.DeliveryMethod> parseDeliveryMethods(List<String> methods) {
//        return methods.stream()
//                .map(Notification.DeliveryMethod::valueOf)
//                .collect(Collectors.toList());
//    }
//
//    @PostMapping("/comment")
//    public Notification createPostCommentNotification(@RequestBody Map<String, Object> payload) {
//        Long recipientId = ((Number) payload.get("recipientId")).longValue();
//        String message = (String) payload.get("message");
//        @SuppressWarnings("unchecked")
//        List<String> deliveryMethodsRaw = (List<String>) payload.get("deliveryMethods");
//        List<Notification.DeliveryMethod> deliveryMethods = parseDeliveryMethods(deliveryMethodsRaw);
//
//        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.THREAD_REPLY, deliveryMethods);
//    }
//
//    @PostMapping("/reply")
//    public Notification createCommentReplyNotification(@RequestBody Map<String, Object> payload) {
//        Long recipientId = ((Number) payload.get("recipientId")).longValue();
//        String message = (String) payload.get("message");
//        @SuppressWarnings("unchecked")
//        List<String> deliveryMethodsRaw = (List<String>) payload.get("deliveryMethods");
//        List<Notification.DeliveryMethod> deliveryMethods = parseDeliveryMethods(deliveryMethodsRaw);
//
//        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.COMMENT_REPLY, deliveryMethods);
//    }
//
//    @PostMapping("/ban")
//    public Notification createBanNotification(@RequestBody Map<String, Object> payload) {
//        Long recipientId = ((Number) payload.get("recipientId")).longValue();
//        String message = (String) payload.get("message");
//        @SuppressWarnings("unchecked")
//        List<String> deliveryMethodsRaw = (List<String>) payload.get("deliveryMethods");
//        List<Notification.DeliveryMethod> deliveryMethods = parseDeliveryMethods(deliveryMethodsRaw);
//
//        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.BAN, deliveryMethods);
//    }
//
//    @PostMapping("/subscribe")
//    public Notification createSubscriptionNotification(@RequestBody Map<String, Object> payload) {
//        Long recipientId = ((Number) payload.get("recipientId")).longValue();
//        String message = (String) payload.get("message");
//        @SuppressWarnings("unchecked")
//        List<String> deliveryMethodsRaw = (List<String>) payload.get("deliveryMethods");
//        List<Notification.DeliveryMethod> deliveryMethods = parseDeliveryMethods(deliveryMethodsRaw);
//
//        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.SUBSCRIPTION, deliveryMethods);
//    }
//
//    @PostMapping("/report")
//    public Notification createReportNotification(@RequestBody Map<String, Object> payload) {
//        Long recipientId = ((Number) payload.get("recipientId")).longValue();
//        String message = (String) payload.get("message");
//        @SuppressWarnings("unchecked")
//        List<String> deliveryMethodsRaw = (List<String>) payload.get("deliveryMethods");
//        List<Notification.DeliveryMethod> deliveryMethods = parseDeliveryMethods(deliveryMethodsRaw);
//
//        return notificationService.sendNotification(recipientId, message, Notification.NotificationType.REPORT, deliveryMethods);
//    }
//
//    @GetMapping("/{userId}")
//    public List<Notification> getUserNotifications(@PathVariable Long userId) {
//        return notificationService.getUserNotifications(userId);
//    }
//
//    @GetMapping("/unread/{userId}")
//    public List<Notification> getUnreadNotifications(@PathVariable Long userId) {
//        return notificationService.getUnreadNotifications(userId);
//    }
//
//    @PutMapping("/{id}/read")
//    public Notification markNotificationAsRead(@PathVariable String id) {
//        return notificationService.markAsRead(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteNotification(@PathVariable String id) {
//        return notificationService.deleteNotification(id);
//    }
//
//    @GetMapping("/test")
//    public String testEndpoint() {
//        return "NotificationApp is running!!!!";
//    }
//
//    @PostMapping("/dummyBan")
//    public Notification createDummyBanNotification() {
//        Long dummyRecipientId = 999L;
//        String dummyMessage = "This is a dummy ban notification for testing";
//        List<Notification.DeliveryMethod> dummyDeliveryMethods = List.of(Notification.DeliveryMethod.IN_APP);
//
//        return notificationService.sendNotification(dummyRecipientId, dummyMessage, Notification.NotificationType.BAN, dummyDeliveryMethods);
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

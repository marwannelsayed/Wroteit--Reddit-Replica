package com.wroteit.UserApp.NotificationApp.controller;
import com.wroteit.UserApp.NotificationApp.model.Notification;
import com.wroteit.UserApp.NotificationApp.service.NotificationService;
import com.wroteit.UserApp.NotificationApp.strategy.DeleteNotificationStrategy;
import com.wroteit.UserApp.NotificationApp.strategy.GetUnreadNotificationsStrategy;
import com.wroteit.UserApp.NotificationApp.strategy.MarkNotificationAsReadStrategy;
import com.wroteit.UserApp.NotificationApp.strategy.NotificationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationFactory notificationFactory;
    private final NotificationContext notificationContext;
    private final DeleteNotificationStrategy deleteNotificationStrategy;
    private final GetUnreadNotificationsStrategy getUnreadNotificationsStrategy;
    private final MarkNotificationAsReadStrategy markNotificationAsReadStrategy;

    @Autowired
    public NotificationController(
            NotificationService notificationService,
            NotificationFactory notificationFactory,
            NotificationContext notificationContext,
            DeleteNotificationStrategy deleteNotificationStrategy,
            GetUnreadNotificationsStrategy getUnreadNotificationsStrategy,
            MarkNotificationAsReadStrategy markNotificationAsReadStrategy) {
        this.notificationService = notificationService;
        this.notificationFactory = notificationFactory;
        this.notificationContext = notificationContext;
        this.deleteNotificationStrategy = deleteNotificationStrategy;
        this.getUnreadNotificationsStrategy = getUnreadNotificationsStrategy;
        this.markNotificationAsReadStrategy = markNotificationAsReadStrategy;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Map<String, Object> notificationDetails) {
        Notification notification = notificationFactory.createNotification(notificationDetails);
        Notification createdNotification = notificationService.createNotification(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsForUser(@PathVariable Long userId) {
        // Using Strategy pattern
        getUnreadNotificationsStrategy.setUserId(userId);
        notificationContext.setStrategy(getUnreadNotificationsStrategy);

        @SuppressWarnings("unchecked")
        List<Notification> unreadNotifications = (List<Notification>) notificationContext.executeStrategy();

        return new ResponseEntity<>(unreadNotifications, HttpStatus.OK);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable String notificationId) {
        // Using Strategy pattern
        markNotificationAsReadStrategy.setNotificationId(notificationId);
        notificationContext.setStrategy(markNotificationAsReadStrategy);

        Notification notification = (Notification) notificationContext.executeStrategy();

        if (notification != null) {
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String notificationId) {
        // Using Strategy pattern
        deleteNotificationStrategy.setNotificationId(notificationId);
        notificationContext.setStrategy(deleteNotificationStrategy);

        Boolean result = (Boolean) notificationContext.executeStrategy();

        if (Boolean.TRUE.equals(result)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
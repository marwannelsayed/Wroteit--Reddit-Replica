
package com.wroteit.NotificationApp.service;

import com.wroteit.NotificationApp.command.CommandInvoker;
import com.wroteit.NotificationApp.command.DeleteNotificationCommand;
import com.wroteit.NotificationApp.command.GetUnreadNotificationsCommand;
import com.wroteit.NotificationApp.command.MarkNotificationAsReadCommand;
import com.wroteit.NotificationApp.model.Notification;
import com.wroteit.NotificationApp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CommandInvoker commandInvoker;
    private final GetUnreadNotificationsCommand getUnreadNotificationsCommand;
    private final MarkNotificationAsReadCommand markNotificationAsReadCommand;
    private final DeleteNotificationCommand deleteNotificationCommand;

    @Autowired
    public NotificationService(
            NotificationRepository notificationRepository,
            CommandInvoker commandInvoker,
            GetUnreadNotificationsCommand getUnreadNotificationsCommand,
            MarkNotificationAsReadCommand markNotificationAsReadCommand,
            DeleteNotificationCommand deleteNotificationCommand) {
        this.notificationRepository = notificationRepository;
        this.commandInvoker = commandInvoker;
        this.getUnreadNotificationsCommand = getUnreadNotificationsCommand;
        this.markNotificationAsReadCommand = markNotificationAsReadCommand;
        this.deleteNotificationCommand = deleteNotificationCommand;
    }

    // Send notif
    public Notification sendNotification(String recipientId, String message, Notification.NotificationType type) {
        Notification notification = new Notification(recipientId, type.name(), message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);       // unread on creation
        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findByRecipientId(userId);
    }

    // Get unread
    @SuppressWarnings("unchecked")
    public List<Notification> getUnreadNotifications(String userId) {
        getUnreadNotificationsCommand.setUserId(userId);
        return (List<Notification>) commandInvoker.executeCommand(getUnreadNotificationsCommand);
    }

    // Mark notification as read
    public Notification markAsRead(String notificationId) {
        markNotificationAsReadCommand.setNotificationId(notificationId);
        return (Notification) commandInvoker.executeCommand(markNotificationAsReadCommand);
    }

    // Delete notif
    public boolean deleteNotification(String notificationId) {
        deleteNotificationCommand.setNotificationId(notificationId);
        return (boolean) commandInvoker.executeCommand(deleteNotificationCommand);
    }
}

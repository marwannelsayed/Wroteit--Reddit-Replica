package com.wroteit.UserApp.NotificationApp.service;
import com.wroteit.UserApp.NotificationApp.command.CommandInvoker;
import com.wroteit.UserApp.NotificationApp.command.DeleteNotificationCommand;
import com.wroteit.UserApp.NotificationApp.command.GetUnreadNotificationsCommand;
import com.wroteit.UserApp.NotificationApp.command.MarkNotificationAsReadCommand;
import com.wroteit.UserApp.NotificationApp.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final CommandInvoker commandInvoker;
    private final GetUnreadNotificationsCommand getUnreadNotificationsCommand;
    private final MarkNotificationAsReadCommand markNotificationAsReadCommand;
    private final DeleteNotificationCommand deleteNotificationCommand;

    @Autowired
    public NotificationService(
            CommandInvoker commandInvoker,
            GetUnreadNotificationsCommand getUnreadNotificationsCommand,
            MarkNotificationAsReadCommand markNotificationAsReadCommand,
            DeleteNotificationCommand deleteNotificationCommand) {
        this.commandInvoker = commandInvoker;
        this.getUnreadNotificationsCommand = getUnreadNotificationsCommand;
        this.markNotificationAsReadCommand = markNotificationAsReadCommand;
        this.deleteNotificationCommand = deleteNotificationCommand;
    }

    @SuppressWarnings("unchecked")
    public List<Notification> getUnreadNotifications(Long userId) {
        getUnreadNotificationsCommand.setUserId(userId);
        return (List<Notification>) commandInvoker.executeCommand(getUnreadNotificationsCommand);
    }

    public Notification markAsRead(Long notificationId) {
        markNotificationAsReadCommand.setNotificationId(notificationId);
        return (Notification) commandInvoker.executeCommand(markNotificationAsReadCommand);
    }

    public boolean deleteNotification(Long notificationId) {
        deleteNotificationCommand.setNotificationId(notificationId);
        return (boolean) commandInvoker.executeCommand(deleteNotificationCommand);
    }
}
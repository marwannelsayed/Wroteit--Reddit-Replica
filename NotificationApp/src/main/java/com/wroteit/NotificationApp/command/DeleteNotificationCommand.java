package com.wroteit.NotificationApp.command;

import com.wroteit.NotificationApp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteNotificationCommand implements Command {
    private final NotificationRepository notificationRepository;
    private String notificationId;

    @Autowired
    public DeleteNotificationCommand(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public Object execute() {
        if (notificationRepository.existsById(notificationId)) {
            notificationRepository.deleteById(notificationId);
            return true;
        }
        return false;
    }
}

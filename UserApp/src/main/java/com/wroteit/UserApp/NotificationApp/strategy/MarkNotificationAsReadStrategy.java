package com.wroteit.UserApp.NotificationApp.strategy;

import com.wroteit.UserApp.NotificationApp.repository.NotificationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarkNotificationAsReadStrategy implements NotificationStrategy {
    private final NotificationRepository notificationRepository;

    @Setter
    private String notificationId;

    @Autowired
    public MarkNotificationAsReadStrategy(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Object execute() {
        // Just check if notification exists
        if (notificationRepository.existsById(notificationId)) {
            // Return true to indicate notification exists, but don't try to update it here
            return true;
        }
        return false;
    }
}
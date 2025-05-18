package com.wroteit.UserApp.NotificationApp.strategy;

import com.wroteit.UserApp.NotificationApp.repository.NotificationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteNotificationStrategy implements NotificationStrategy {
    private final NotificationRepository notificationRepository;

    @Setter
    private String notificationId;

    @Autowired
    public DeleteNotificationStrategy(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Object execute() {
        Optional<?> optionalNotification = notificationRepository.findById(notificationId);
        Optional<com.wroteit.UserApp.NotificationApp.model.Notification> notification =
                (Optional<com.wroteit.UserApp.NotificationApp.model.Notification>) optionalNotification;
        if (notification.isPresent()) {
            notificationRepository.deleteById(notificationId);
            return true;
        }
        return false;
    }
}
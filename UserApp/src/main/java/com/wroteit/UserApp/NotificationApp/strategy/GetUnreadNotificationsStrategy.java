package com.wroteit.UserApp.NotificationApp.strategy;

import com.wroteit.UserApp.NotificationApp.repository.NotificationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUnreadNotificationsStrategy implements NotificationStrategy {
    private final NotificationRepository notificationRepository;

    @Setter
    private Long userId;

    @Autowired
    public GetUnreadNotificationsStrategy(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Object execute() {
        // Return the raw result as an Object, which will be cast by the caller
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }
}
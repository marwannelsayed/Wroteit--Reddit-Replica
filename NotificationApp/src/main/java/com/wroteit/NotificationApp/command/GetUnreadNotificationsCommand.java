package com.wroteit.NotificationApp.command;

import com.wroteit.NotificationApp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetUnreadNotificationsCommand implements Command {
    private final NotificationRepository notificationRepository;
    private String userId;

    @Autowired
    public GetUnreadNotificationsCommand(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public Object execute() {
        return notificationRepository.findByRecipientIdAndIsReadFalse(userId);
    }
}

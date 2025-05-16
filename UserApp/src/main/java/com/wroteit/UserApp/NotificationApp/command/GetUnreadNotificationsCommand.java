package com.wroteit.UserApp.NotificationApp.command;
import com.wroteit.UserApp.NotificationApp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUnreadNotificationsCommand implements Command {
    private final NotificationRepository notificationRepository;
    private Long userId;

    @Autowired
    public GetUnreadNotificationsCommand(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public Object execute() {
        return notificationRepository.findByRecipientIdAndIsReadFalse(userId);
    }
}
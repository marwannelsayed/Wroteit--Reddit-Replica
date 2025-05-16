package com.wroteit.UserApp.NotificationApp.command;
import com.wroteit.UserApp.NotificationApp.model.Notification;
import com.wroteit.UserApp.NotificationApp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarkNotificationAsReadCommand implements Command {
    private final NotificationRepository notificationRepository;
    private Long notificationId;

    @Autowired
    public MarkNotificationAsReadCommand(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public Object execute() {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            return notificationRepository.save(notification);
        }
        return null;
    }
}
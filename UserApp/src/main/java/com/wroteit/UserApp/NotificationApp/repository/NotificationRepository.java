package com.wroteit.UserApp.NotificationApp.repository;
import com.wroteit.UserApp.NotificationApp.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdAndIsReadFalse(Long recipientId);
    Notification findById(long id);
    void deleteById(long id);
}
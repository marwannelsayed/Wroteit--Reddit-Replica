
package com.wroteit.NotificationApp.repository;

import com.wroteit.NotificationApp.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByRecipientId(Long recipientId);
    List<Notification> findByRecipientIdAndIsReadFalse(Long recipientId);
}

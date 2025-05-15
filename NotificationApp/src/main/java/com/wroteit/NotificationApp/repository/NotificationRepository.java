package com.wroteit.NotificationApp.repository;

import com.wroteit.NotificationApp.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByRecipientId(String recipientId);
}

//package com.wroteit.notification.repository;
//
//import com.wroteit.notification.model.Notification;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import java.util.List;
//
//public interface NotificationRepository extends MongoRepository<Notification, String> {
//    List<Notification> findByRecipientId(String recipientId);
//}

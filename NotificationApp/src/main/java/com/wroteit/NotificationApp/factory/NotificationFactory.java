package com.wroteit.NotificationApp.factory;

import com.wroteit.NotificationApp.model.Notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationFactory {

    public static Notification createNotification(Long recipientId, String message, Notification.NotificationType type, List<Notification.DeliveryMethod> deliveryMethods) {
        Notification notification = new Notification(
                recipientId,
                type,
                message
        );
        if(deliveryMethods.isEmpty()){
            notification.setDeliveryMethods(List.of(Notification.DeliveryMethod.IN_APP));
        }else{
            notification.setDeliveryMethods(deliveryMethods);
        }
        notification.setRead(false);                 // set unread
        notification.setCreatedAt(LocalDateTime.now());
        return notification;
    }
}


//package com.wroteit.NotificationApp.factory;
//
//import com.wroteit.NotificationApp.model.Notification;
//
//import java.time.LocalDateTime;
//
//public class NotificationFactory {
//
//    public static Notification createNotification(String recipientId, String message, Notification.NotificationType type) {
//        return new Notification(
//                recipientId,
//                message,
//                type,
//                Notification.NotificationStatus.UNREAD,
//                LocalDateTime.now()
//        );
//    }
//}

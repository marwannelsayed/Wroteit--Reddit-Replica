package com.wroteit.UserApp.NotificationApp;
import com.wroteit.UserApp.NotificationApp.model.Notification;
import java.time.LocalDateTime;
import java.util.Map;

public class NotificationMapper {

    @SuppressWarnings("unchecked")
    public static Notification toNotification(Object source) {
        if (source == null) {
            return null;
        }

        if (source instanceof com.wroteit.UserApp.NotificationApp.model.Notification) {
            return (Notification) source;
        }

      if (source instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) source;

            Notification notification = new Notification();

            if (map.containsKey("id")) {
                notification.setId(map.get("id").toString());
            }

            if (map.containsKey("userId")) {
                Object userId = map.get("userId");
                if (userId instanceof Long) {
                    notification.setUserId((Long) userId);
                } else if (userId instanceof Integer) {
                    notification.setUserId(((Integer) userId).longValue());
                } else if (userId instanceof String) {
                    notification.setUserId(Long.parseLong((String) userId));
                }
            }

            if (map.containsKey("type")) {
                notification.setType((String) map.get("type"));
            }

            if (map.containsKey("content")) {
                notification.setContent((String) map.get("content"));
            }

            if (map.containsKey("isRead")) {
                notification.setRead((Boolean) map.get("isRead"));
            }

            if (map.containsKey("createdAt")) {
                Object createdAt = map.get("createdAt");
                if (createdAt instanceof LocalDateTime) {
                    notification.setCreatedAt((LocalDateTime) createdAt);
                } else {
                    notification.setCreatedAt(LocalDateTime.now());
                }
            } else {
                notification.setCreatedAt(LocalDateTime.now());
            }

            if (map.containsKey("updatedAt")) {
                Object updatedAt = map.get("updatedAt");
                if (updatedAt instanceof LocalDateTime) {
                    notification.setUpdatedAt((LocalDateTime) updatedAt);
                } else {
                    notification.setUpdatedAt(LocalDateTime.now());
                }
            } else {
                notification.setUpdatedAt(LocalDateTime.now());
            }

            return notification;
        }

       try {
            Notification notification = new Notification();

            java.lang.reflect.Method getIdMethod = source.getClass().getMethod("getId");
            Object id = getIdMethod.invoke(source);
            if (id != null) {
                notification.setId(id.toString());
            }

            java.lang.reflect.Method getUserIdMethod = source.getClass().getMethod("getUserId");
            Object userId = getUserIdMethod.invoke(source);
            if (userId instanceof Long) {
                notification.setUserId((Long) userId);
            }

            return notification;
        } catch (Exception e) {
            return new Notification();
        }
    }
}
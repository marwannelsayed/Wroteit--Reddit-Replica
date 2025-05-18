package com.example.threadapp.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @RabbitListener(queues = "notification.queue")
    public void receive(String message) {
        System.out.println("📥 Notification received: " + message);
    }
}

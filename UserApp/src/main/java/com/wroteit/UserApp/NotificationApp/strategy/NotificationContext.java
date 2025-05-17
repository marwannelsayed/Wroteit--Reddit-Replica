package com.wroteit.UserApp.NotificationApp.strategy;

import org.springframework.stereotype.Component;

@Component
public class NotificationContext {
    private NotificationStrategy strategy;

    public void setStrategy(NotificationStrategy strategy) {
        this.strategy = strategy;
    }

    public Object executeStrategy() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy has not been set");
        }
        return strategy.execute();
    }
}
package com.wroteit.NotificationApp.command;

import org.springframework.stereotype.Component;

@Component
public class CommandInvoker {
    public Object executeCommand(Command command) {
        return command.execute();
    }
}

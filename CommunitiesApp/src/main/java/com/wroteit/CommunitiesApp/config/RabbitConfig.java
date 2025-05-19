package com.wroteit.CommunitiesApp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue deleteUserQueue() {
        return new Queue("deleteUserQueue", false);
    }
}


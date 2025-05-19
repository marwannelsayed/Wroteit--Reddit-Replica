package com.example.ThreadsApp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;


@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange moderatorExchange() {
        return new TopicExchange("moderator.exchange");
    }

    @Bean
    public Queue reportQueue() {
        return new Queue("moderator.report.queue");
    }

    @Bean
    public Binding binding(Queue reportQueue, TopicExchange moderatorExchange) {
        return BindingBuilder.bind(reportQueue).to(moderatorExchange).with("moderator.report");
    }
}



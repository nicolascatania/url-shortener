package com.ncatania.userservice.infraestructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "user-events-exchange";
    public static final String QUEUE = "user-creation-email-queue";
    public static final String ROUTING_KEY = "user.created";

    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding binding(Queue emailQueue, TopicExchange userEventsExchange) {
        return BindingBuilder
                .bind(emailQueue)
                .to(userEventsExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
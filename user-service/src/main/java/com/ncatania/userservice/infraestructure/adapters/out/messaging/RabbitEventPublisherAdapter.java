package com.ncatania.userservice.infraestructure.adapters.out.messaging;

import com.ncatania.userservice.application.event.UserCreatedEvent;
import com.ncatania.userservice.application.ports.out.smtp.EmailPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitEventPublisherAdapter implements EmailPublisherPort {


    private final RabbitTemplate rabbitTemplate;
    public static final String EXCHANGE = "user-events-exchange";
    public static final String ROUTING_KEY = "user.created";

    @Override
    public void publishUserCreated(UserCreatedEvent event) {
        log.info("Publishing user created event: {}", event);
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
    }
}
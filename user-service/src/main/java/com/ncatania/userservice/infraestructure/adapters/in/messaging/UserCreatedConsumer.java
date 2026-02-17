package com.ncatania.userservice.infraestructure.adapters.in.messaging;

import com.ncatania.userservice.application.event.UserCreatedEvent;
import com.ncatania.userservice.infraestructure.adapters.out.smtp.JavaMailAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreatedConsumer {

    private final JavaMailAdapter mailAdapter;

    @RabbitListener(queues = "user-creation-email-queue")
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Received user created event on listener: {}", event);
        mailAdapter.sendWelcomeEmail(event.email(), event.name());
    }
}
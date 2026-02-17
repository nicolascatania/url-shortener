package com.ncatania.userservice.infraestructure.adapters.in.messaging;

import com.ncatania.userservice.application.event.UserCreatedEvent;
import com.ncatania.userservice.infraestructure.adapters.out.smtp.JavaMailAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedConsumer {

    private final JavaMailAdapter mailAdapter;

    @RabbitListener(queues = "user-creation-email-queue")
    public void handleUserCreated(UserCreatedEvent event) {
        mailAdapter.sendWelcomeEmail(event.email(), event.name());
    }
}
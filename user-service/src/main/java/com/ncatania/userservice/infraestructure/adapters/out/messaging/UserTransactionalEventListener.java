package com.ncatania.userservice.infraestructure.adapters.out.messaging;

import com.ncatania.userservice.application.event.UserCreatedEvent;
import com.ncatania.userservice.application.ports.out.smtp.EmailPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserTransactionalEventListener {

    private final EmailPublisherPort rabbitPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreatedCommit(UserCreatedEvent event) {
        log.info("Transaction committed for user {}. Sending to RabbitMQ...", event.email());
        rabbitPublisher.publishUserCreated(event);
    }
}
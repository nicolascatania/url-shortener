package com.ncatania.userservice.application.ports.out.smtp;

import com.ncatania.userservice.application.event.UserCreatedEvent;

public interface EmailPublisherPort {
    void publishUserCreated(UserCreatedEvent event);
}

package com.ncatania.userservice.application.event;

public record UserCreatedEvent(
        Long userId,
        String email,
        String name
) {}
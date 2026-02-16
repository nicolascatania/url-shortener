package com.ncatania.userservice.application.dto;

public record UserResponse (
        Long id,
        String name,
        String email
) {
}

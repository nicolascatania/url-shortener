package com.ncatania.userservice.application.dto;

public record UserRequest(
        String name,
        String email,
        String password
) {
}

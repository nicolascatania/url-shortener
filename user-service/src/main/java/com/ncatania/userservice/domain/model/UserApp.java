package com.ncatania.userservice.domain.model;

public record UserApp (
        Long id,
        String name,
        String email,
        String password
) {
}

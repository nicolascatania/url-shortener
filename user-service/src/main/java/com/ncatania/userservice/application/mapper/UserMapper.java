package com.ncatania.userservice.application.mapper;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.dto.UserResponse;
import com.ncatania.userservice.domain.model.UserApp;
import com.ncatania.userservice.infraestructure.adapters.out.persistence.UserAppEntity;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(UserAppEntity entity) {
        if (entity == null) return null;
        return new UserResponse(entity.getId(), entity.getUsername(), entity.getEmail());
    }

    public static UserAppEntity toEntity(UserRequest request) {
        if (request == null) return null;
        UserAppEntity entity = new UserAppEntity();
        entity.setUsername(request.name());
        entity.setEmail(request.email());
        entity.setPassword(request.password());
        return entity;
    }

    public static UserAppEntity toEntity(UserApp domain) {
        if (domain == null) return null;
        UserAppEntity entity = new UserAppEntity();
        entity.setId(domain.id());
        entity.setUsername(domain.name());
        entity.setEmail(domain.email());
        entity.setPassword(domain.password());
        return entity;
    }

    public static UserApp toDomain(UserAppEntity entity) {
        if (entity == null) return null;
        return new UserApp(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword());
    }

    public static UserResponse toResponse(UserApp domain) {
        if (domain == null) return null;
        return new UserResponse(domain.id(), domain.name(), domain.email());
    }
}

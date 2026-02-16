package com.ncatania.userservice.application.ports.out;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.dto.UserResponse;

import java.util.List;

public interface UserRepositoryPort {
    List<UserResponse> getAll();
    UserResponse getById(Long id);
    UserResponse create(UserRequest user);
    void deleteById(Long id);
}

package com.ncatania.userservice.application.ports.in;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.dto.UserResponse;

import java.util.List;

public interface UserServiceUseCase {
    List<UserResponse> getAll();
    UserResponse getById(Long id);
    UserResponse create(UserRequest userRequest);
    void deleteById(Long id);
}

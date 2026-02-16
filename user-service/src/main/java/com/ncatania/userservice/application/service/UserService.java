package com.ncatania.userservice.application.service;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.dto.UserResponse;
import com.ncatania.userservice.application.ports.in.UserServiceUseCase;
import com.ncatania.userservice.application.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceUseCase {

    private final UserRepositoryPort userRepository;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.getAll();
    }

    @Override
    public UserResponse getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest userRequest) {
        return userRepository.create(userRequest);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

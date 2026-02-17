package com.ncatania.userservice.application.service;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.dto.UserResponse;
import com.ncatania.userservice.application.event.UserCreatedEvent;
import com.ncatania.userservice.application.mapper.UserMapper;
import com.ncatania.userservice.application.ports.in.UserServiceUseCase;
import com.ncatania.userservice.application.ports.out.UserRepositoryPort;
import com.ncatania.userservice.domain.model.UserApp;
import com.ncatania.userservice.infraestructure.exception.UserNotFoundException;
import com.ncatania.userservice.infraestructure.security.PasswordHasher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserServiceUseCase {

    private final UserRepositoryPort userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.getAll().stream().map(UserMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse getById(Long id) {
        return userRepository.getById(id).map(UserMapper::toResponse).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest userRequest) {
        // Hash password before saving
        log.debug("Hashing password for user: {}", userRequest.email());
        String hashedPassword = PasswordHasher.hashPassword(userRequest.password());
        UserRequest secureUserRequest = new UserRequest(
                userRequest.name(),
                userRequest.email(),
                hashedPassword
        );



        UserApp user = userRepository.create(UserMapper.toDomain(secureUserRequest));
        log.info("New user created successfully with ID: {} and email: {}", user.id(), user.email());

        UserResponse response = UserMapper.toResponse(user);

        log.debug("Publishing UserCreatedEvent for ID: {}", response.id());
        eventPublisher.publishEvent(new UserCreatedEvent(
                response.id(),
                response.email(),
                response.name()
        ));


        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

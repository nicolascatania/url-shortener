package com.ncatania.userservice.application.service;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.dto.UserResponse;
import com.ncatania.userservice.application.event.UserCreatedEvent;
import com.ncatania.userservice.application.ports.out.UserRepositoryPort;
import com.ncatania.userservice.domain.model.UserApp;
import com.ncatania.userservice.infraestructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private UserService userService;

    @Test
    void getAll_shouldReturnListOfUserResponse() {
        // Arrange
        UserApp userDomain = new UserApp(1L, "John", "john@example.com", "hash");
        when(userRepository.getAll()).thenReturn(List.of(userDomain));

        // Act
        List<UserResponse> result = userService.getAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).name());
        verify(userRepository).getAll();
    }

    @Test
    void getById_shouldReturnUserResponse_WhenUserExists() {
        // Arrange
        UserApp userDomain = new UserApp(1L, "John", "john@example.com", "hash");
        when(userRepository.getById(1L)).thenReturn(Optional.of(userDomain));

        // Act
        UserResponse result = userService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.name());
    }

    @Test
    void getById_shouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.getById(99L)).thenReturn(Optional.empty());

        // Assert
        assertThrows(UserNotFoundException.class, () -> userService.getById(99L));
    }

    @Test
    void create_shouldHashPasswordAndPublishEvent() {
        // Arrange
        UserRequest request = new UserRequest("John", "john@example.com", "rawPassword");
        UserApp savedDomain = new UserApp(1L, "John", "john@example.com", "hashedPassword");

        when(userRepository.create(any(UserApp.class))).thenReturn(savedDomain);

        // Act
        UserResponse result = userService.create(request);

        // Assert
        assertNotNull(result);

        // Verificamos que el password que llegó al repo NO sea el original
        ArgumentCaptor<UserApp> userCaptor = ArgumentCaptor.forClass(UserApp.class);
        verify(userRepository).create(userCaptor.capture());
        assertNotEquals("rawPassword", userCaptor.getValue().password());

        // Verificamos que se publicó el evento
        verify(eventPublisher, times(1)).publishEvent(any(UserCreatedEvent.class));
    }
}
package com.ncatania.userservice.application.service;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.dto.UserResponse;
import com.ncatania.userservice.application.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAll_shouldReturnListOfUsers() {
        UserResponse user1 = new UserResponse(1L, "John", "john@example.com");
        UserResponse user2 = new UserResponse(2L, "Jane", "jane@example.com");
        List<UserResponse> users = List.of(user1, user2);

        when(userRepository.getAll()).thenReturn(users);

        List<UserResponse> result = userService.getAll();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).name());
        verify(userRepository, times(1)).getAll();
    }

    @Test
    void getById_shouldReturnUser() {
        UserResponse user = new UserResponse(1L, "John", "john@example.com");
        when(userRepository.getById(1L)).thenReturn(user);

        UserResponse result = userService.getById(1L);

        assertNotNull(result);
        assertEquals("John", result.name());
        assertEquals("john@example.com", result.email());
        verify(userRepository, times(1)).getById(1L);
    }

    @Test
    void getById_notFound_shouldReturnNull() {
        when(userRepository.getById(999L)).thenReturn(null);

        UserResponse result = userService.getById(999L);

        assertNull(result);
        verify(userRepository, times(1)).getById(999L);
    }

    @Test
    void create_shouldReturnCreatedUser() {
        UserRequest request = new UserRequest("John", "john@example.com", "password123");
        UserResponse createdUser = new UserResponse(1L, "John", "john@example.com");

        when(userRepository.create(any(UserRequest.class))).thenReturn(createdUser);

        UserResponse result = userService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John", result.name());
        verify(userRepository, times(1)).create(any(UserRequest.class));
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        userService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}

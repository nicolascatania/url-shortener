package com.ncatania.userservice.infraestructure.adapters.out.persistence;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.mapper.UserMapper;
import com.ncatania.userservice.application.ports.out.UserRepositoryPort;
import com.ncatania.userservice.domain.model.UserApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class JPARepositoryAdapterIT {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"))
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private UserRepositoryPort userRepository;

    @Test
    void createAndRetrieveUser() {
        // Arrange
        UserApp userToSave = new UserApp(null, "John Doe", "john@example.com", "secretHash");

        // Act
        UserApp savedUser = userRepository.create(userToSave);

        // Assert
        assertNotNull(savedUser.id());
        assertEquals("John Doe", savedUser.name());

        Optional<UserApp> retrieved = userRepository.getById(savedUser.id());
        assertTrue(retrieved.isPresent());
        assertEquals("john@example.com", retrieved.get().email());
    }

    @Test
    void getAllUsers() {
        userRepository.create(new UserApp(null, "User1", "u1@test.com", "p1"));
        userRepository.create(new UserApp(null, "User2", "u2@test.com", "p2"));

        List<UserApp> users = userRepository.getAll();
        assertTrue(users.size() >= 2);
    }

    @Test
    void deleteUser() {
        UserApp user = userRepository.create(new UserApp(null, "DeleteMe", "del@test.com", "p"));
        Long id = user.id();

        userRepository.deleteById(id);

        Optional<UserApp> deleted = userRepository.getById(id);
        assertTrue(deleted.isEmpty());
    }
}
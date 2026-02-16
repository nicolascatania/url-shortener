package com.ncatania.userservice.infraestructure.adapters.out.persistence;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

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
        UserRequest request = new UserRequest("John Doe", "john@example.com", "password123");
        var savedUser = userRepository.create(request);

        assertNotNull(savedUser);
        assertNotNull(savedUser.id());
        assertEquals("John Doe", savedUser.name());
        assertEquals("john@example.com", savedUser.email());

        var retrieved = userRepository.getById(savedUser.id());
        assertNotNull(retrieved);
        assertEquals("John Doe", retrieved.name());
    }

    @Test
    void getAllUsers() {
        userRepository.create(new UserRequest("User1", "user1@example.com", "pass1"));
        userRepository.create(new UserRequest("User2", "user2@example.com", "pass2"));

        var users = userRepository.getAll();
        assertTrue(users.size() >= 2);
    }

    @Test
    void deleteUser() {
        var user = userRepository.create(new UserRequest("Delete", "del@example.com", "pass"));
        userRepository.deleteById(user.id());
        var deleted = userRepository.getById(user.id());
        assertNull(deleted);
    }
}

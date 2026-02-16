package com.ncatania.userservice.infraestructure.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHasherTest {

    @Test
    void hashPassword_shouldReturnHashedPassword() {
        String password = "mySecurePassword123";
        String hashed = PasswordHasher.hashPassword(password);

        assertNotNull(hashed);
        assertNotEquals(password, hashed);
        assertTrue(hashed.contains(":"), "Hash should contain salt:hash format");
    }

    @Test
    void hashPassword_shouldReturnDifferentHashForSamePassword() {
        String password = "mySecurePassword123";
        String hash1 = PasswordHasher.hashPassword(password);
        String hash2 = PasswordHasher.hashPassword(password);

        assertNotEquals(hash1, hash2, "Each hash should be unique due to random salt");
    }

    @Test
    void verifyPassword_shouldReturnTrueForCorrectPassword() {
        String password = "mySecurePassword123";
        String hashed = PasswordHasher.hashPassword(password);

        assertTrue(PasswordHasher.verifyPassword(password, hashed));
    }

    @Test
    void verifyPassword_shouldReturnFalseForWrongPassword() {
        String password = "mySecurePassword123";
        String wrongPassword = "wrongPassword";
        String hashed = PasswordHasher.hashPassword(password);

        assertFalse(PasswordHasher.verifyPassword(wrongPassword, hashed));
    }

    @Test
    void verifyPassword_shouldReturnFalseForInvalidHash() {
        assertFalse(PasswordHasher.verifyPassword("password", "invalid-hash-format"));
    }
}

package com.aminsinho.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testUser() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com", "testuser", "password", null);

        assertEquals("test@example.com", user.getEmail());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }
}
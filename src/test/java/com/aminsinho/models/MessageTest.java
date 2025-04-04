package com.aminsinho.models;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @Test
    void testMessage() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com", "testuser", "password", null);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message = new Message(UUID.randomUUID(), "Hello, game!", timestamp, user);

        assertEquals("Hello, game!", message.getMessage());
        assertEquals(timestamp, message.getTimestamp());
        assertEquals(user, message.getUser());
    }
}
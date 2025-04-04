package com.aminsinho.models;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {

    @Test
    void testResponse() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com", "testuser", "password", null);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Response response = new Response(UUID.randomUUID(), "Response text", timestamp, user);

        assertEquals("Response text", response.getResponse());
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(user, response.getUser());
    }
}
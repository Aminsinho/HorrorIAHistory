package com.aminsinho.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameSessionTest {

    @Test
    void testGameSession() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com", "testuser", "password", null);
        GameSession gameSession = new GameSession(UUID.randomUUID(), "Estado inicial...", user);

        assertEquals("Estado inicial...", gameSession.getCurrentState());
        assertEquals(user, gameSession.getUser());
    }
}
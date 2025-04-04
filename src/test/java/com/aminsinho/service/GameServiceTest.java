package com.aminsinho.service;

import com.aminsinho.models.GameSession;
import com.aminsinho.models.Message;
import com.aminsinho.models.Response;
import com.aminsinho.models.User;
import com.aminsinho.repository.GameRepository;
import com.aminsinho.repository.MessageRepository;
import com.aminsinho.repository.ResponseRepository;
import com.aminsinho.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ResponseRepository responseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService(gameRepository, messageRepository, responseRepository, userRepository) {
            @Override
            public String callOllama(String message) {
                return "Mocked response from Ollama";
            }
        };
    }

    @Test
    void testStartGame() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com", "testuser", "password", null);
        GameSession gameSession = new GameSession(null, "Estado inicial...", user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.save(any(GameSession.class))).thenReturn(gameSession);

        GameSession result = gameService.startGame(userId);

        assertEquals("Estado inicial...", result.getCurrentState());
        verify(userRepository, times(1)).findById(userId);
        verify(gameRepository, times(1)).save(any(GameSession.class));
    }

    @Test
    void testSendMessage() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com", "testuser", "password", null);
        Message message = new Message("Hello, game!");
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));

        when(gameRepository.findByUserId(userId)).thenReturn(new GameSession(userId, "Estado inicial...", user));
        when(messageRepository.findByUserId(userId)).thenReturn(List.of(message));
        when(responseRepository.findByUserId(userId)).thenReturn(List.of(new Response("Response 1", new Timestamp(System.currentTimeMillis()), user)));

        gameService.sendMessage(userId, message);

        verify(messageRepository, times(1)).save(message);
        verify(responseRepository, times(1)).save(any(Response.class));
    }

    @Test
    void testGetMessages() {
        UUID userId = UUID.randomUUID();
        List<Message> messages = List.of(new Message("Message 1"), new Message("Message 2"));

        when(messageRepository.findByUserId(userId)).thenReturn(messages);

        List<Message> result = gameService.getMessages(userId);

        assertEquals(2, result.size());
        assertEquals("Message 1", result.get(0).getMessage());
        verify(messageRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetResponses() {
        UUID userId = UUID.randomUUID();
        List<Response> responses = List.of(new Response("Response 1"), new Response("Response 2"));

        when(responseRepository.findByUserId(userId)).thenReturn(responses);

        List<Response> result = gameService.getResponses(userId);

        assertEquals(2, result.size());
        assertEquals("Response 1", result.get(0).getResponse());
        verify(responseRepository, times(1)).findByUserId(userId);
    }
}
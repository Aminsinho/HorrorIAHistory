package com.aminsinho.controller;

import com.aminsinho.models.GameSession;
import com.aminsinho.models.Message;
import com.aminsinho.models.Response;
import com.aminsinho.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    void testStartGameSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        GameSession gameSession = new GameSession(userId, "Estado inicial...");

        when(gameService.startGame(userId)).thenReturn(gameSession);

        mockMvc.perform(post("/game/start")
                        .requestAttr("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"" + userId + "\",\"currentState\":\"Estado inicial...\"}"));

        verify(gameService, times(1)).startGame(userId);
    }

    @Test
    void testStartGameFailure() throws Exception {
        UUID userId = UUID.randomUUID();

        when(gameService.startGame(userId)).thenThrow(new RuntimeException("Game start failed"));

        mockMvc.perform(post("/game/start")
                        .requestAttr("userId", userId.toString()))
                .andExpect(status().isInternalServerError());

        verify(gameService, times(1)).startGame(userId);
    }

    @Test
    void testSendMessageSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        Message message = new Message("Hello, game!");

        doNothing().when(gameService).sendMessage(userId, message);

        mockMvc.perform(post("/game/message")
                        .requestAttr("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Hello, game!\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Hello, game!\"}"));

        verify(gameService, times(1)).sendMessage(userId, message);
    }

    @Test
    void testSendMessageFailure() throws Exception {
        UUID userId = UUID.randomUUID();
        Message message = new Message("Hello, game!");

        doThrow(new RuntimeException("Message sending failed")).when(gameService).sendMessage(userId, message);

        mockMvc.perform(post("/game/message")
                        .requestAttr("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Hello, game!\"}"))
                .andExpect(status().isInternalServerError());

        verify(gameService, times(1)).sendMessage(userId, message);
    }

    @Test
    void testGetMessagesSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        List<Message> messages = List.of(new Message("Message 1"), new Message("Message 2"));

        when(gameService.getMessages(userId)).thenReturn(messages);

        mockMvc.perform(get("/game/messages")
                        .requestAttr("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"message\":\"Message 1\"},{\"message\":\"Message 2\"}]"));

        verify(gameService, times(1)).getMessages(userId);
    }

    @Test
    void testGetMessagesFailure() throws Exception {
        UUID userId = UUID.randomUUID();

        when(gameService.getMessages(userId)).thenThrow(new RuntimeException("Failed to fetch messages"));

        mockMvc.perform(get("/game/messages")
                        .requestAttr("userId", userId.toString()))
                .andExpect(status().isInternalServerError());

        verify(gameService, times(1)).getMessages(userId);
    }

    @Test
    void testGetResponsesSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        List<Response> responses = List.of(new Response("Response 1"), new Response("Response 2"));

        when(gameService.getResponses(userId)).thenReturn(responses);

        mockMvc.perform(get("/game/responses")
                        .requestAttr("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"response\":\"Response 1\"},{\"response\":\"Response 2\"}]"));

        verify(gameService, times(1)).getResponses(userId);
    }

    @Test
    void testGetResponsesFailure() throws Exception {
        UUID userId = UUID.randomUUID();

        when(gameService.getResponses(userId)).thenThrow(new RuntimeException("Failed to fetch responses"));

        mockMvc.perform(get("/game/responses")
                        .requestAttr("userId", userId.toString()))
                .andExpect(status().isInternalServerError());

        verify(gameService, times(1)).getResponses(userId);
    }
}
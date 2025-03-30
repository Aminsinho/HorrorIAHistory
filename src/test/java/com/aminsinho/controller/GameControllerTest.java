package com.aminsinho.controller;

import com.aminsinho.iservice.GameServiceInterface;
import com.aminsinho.models.GameSession;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GameControllerTest {

    @Mock
    private GameServiceInterface gameService;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    void testGetRecommendedGames() throws Exception {
        GameSession game = new GameSession(UUID.randomUUID(), "Game 1");
        System.out.println("Created game: " + game);

        System.out.println("Mocked gameService.getRecommendedGames() to return: " + List.of(game));

        mockMvc.perform(get("/api/games/recommendations"))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println("Response status: " + result.getResponse().getStatus()))
                .andExpect(content().json("[{\"id\":1,\"title\":\"Game 1\"}]"))
                .andDo(result -> System.out.println("Response content: " + result.getResponse().getContentAsString()));
    }

    @Test
    void testSaveGame() throws Exception {
        GameSession game = new GameSession(UUID.randomUUID(), "Game 1");

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Game 1\"}"))
                .andExpect(status().isOk());
    }
}
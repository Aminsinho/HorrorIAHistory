package com.aminsinho.service;

import com.aminsinho.models.GameSession;
import com.aminsinho.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;


    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
    @Test
    void testGetRecommendedGames() {
        GameSession game1 = new GameSession(UUID.randomUUID(), "Game 1");
        GameSession game2 = new GameSession(UUID.randomUUID(), "Game 2");

        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));


        assertEquals(2, recommendedGames.size());
        assertEquals(game1, recommendedGames.get(0));
    }
     */

    /**
    @Test
    void testSaveGame() {
        GameSession game = new GameSession(UUID.randomUUID(), "Game 1");

        gameService.saveGame(game);

        verify(gameRepository, times(1)).save(game);
    }
    */
}
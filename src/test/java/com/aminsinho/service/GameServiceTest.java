package com.aminsinho.service;

import com.aminsinho.models.Game;
import com.aminsinho.repository.GameRepository;
import com.aminsinho.repository.RecommendationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecommendedGames() {
        Game game1 = new Game(1L, "Game 1");
        Game game2 = new Game(2L, "Game 2");

        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));
        when(recommendationRepository.existsByGameId(1L)).thenReturn(false);
        when(recommendationRepository.existsByGameId(2L)).thenReturn(true);

        List<Game> recommendedGames = gameService.getRecommendedGames();

        assertEquals(1, recommendedGames.size());
        assertEquals(game1, recommendedGames.get(0));
    }

    @Test
    void testSaveGame() {
        Game game = new Game(1L, "Game 1");

        gameService.saveGame(game);

        verify(gameRepository, times(1)).save(game);
    }
}
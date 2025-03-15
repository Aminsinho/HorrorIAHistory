package com.aminsinho.service;

import com.aminsinho.iservice.GameServiceInterface;
import com.aminsinho.models.Game;
import com.aminsinho.repository.GameRepository;
import com.aminsinho.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService implements GameServiceInterface {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    public List<Game> getRecommendedGames() {
        List<Game> allGames = gameRepository.findAll();
        return allGames.stream()
                .filter(game -> !recommendationRepository.existsByGameId(game.getId()))
                .collect(Collectors.toList());
    }


    public void saveGame(Game game) {
        gameRepository.save(game);
    }
}

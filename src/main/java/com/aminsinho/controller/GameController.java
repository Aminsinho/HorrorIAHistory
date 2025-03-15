package com.aminsinho.controller;

import com.aminsinho.iservice.GameServiceInterface;
import com.aminsinho.models.Game;
import com.aminsinho.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {


    @Autowired
    private GameServiceInterface gameService;

    @GetMapping("/recommendations")
    public List<Game> getRecommendedGames() {
        return gameService.getRecommendedGames();
    }

    @PostMapping
    public void saveGame(@RequestBody Game game) {
        gameService.saveGame(game);
    }
}


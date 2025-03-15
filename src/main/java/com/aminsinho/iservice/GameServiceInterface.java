package com.aminsinho.iservice;


import com.aminsinho.models.Game;

import java.util.List;

public interface GameServiceInterface {

    List<Game> getRecommendedGames();
    void saveGame(Game game);
}

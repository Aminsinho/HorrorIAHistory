package com.aminsinho.iservice;


import com.aminsinho.models.GameSession;

import java.util.List;
import java.util.UUID;

public interface GameServiceInterface {

    GameSession startGame(UUID userId);

    GameSession makeDecision(UUID userId, String decision);

    GameSession rollback(UUID userId);


}

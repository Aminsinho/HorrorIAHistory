package com.aminsinho.iservice;


import com.aminsinho.models.GameSession;
import com.aminsinho.models.Message;
import com.aminsinho.models.Response;

import java.util.List;
import java.util.UUID;

public interface GameServiceInterface {

    GameSession startGame(UUID userId);
    
    Message sendMessage(UUID userId, Message message);

    List<Message> getMessages(UUID userId);

    List<Response> getResponses(UUID userId);
}

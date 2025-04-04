package com.aminsinho.service;

import com.aminsinho.iservice.GameServiceInterface;
import com.aminsinho.models.*;
import com.aminsinho.prompt.Prompt;
import com.aminsinho.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static com.aminsinho.prompt.Prompt.CONTINUE_PROMPT;
import static com.aminsinho.prompt.Prompt.INITIAL_PROMPT;

@Service
@RequiredArgsConstructor
public class GameService implements GameServiceInterface {
    private final GameRepository gameSessionRepository;
    private final MessageRepository messageRepository;
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final OllamaService ollamaService;

    @Transactional
    public GameSession startGame(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        GameSession session = new GameSession(null, "Estado inicial...", user);
        ollamaService.callOllama(INITIAL_PROMPT, user);
        return gameSessionRepository.save(session);
    }



    private GameSession getGameSession(UUID userId) {
        GameSession session = gameSessionRepository.findByUserId(userId);

        if (session == null) {
            throw new RuntimeException("Partida no encontrada");
        }

        return session;
    }

    @Transactional
    public void sendMessage(UUID userId, Message message) {
        User user = getGameSession(userId).getUser();
        message.setUser(user);

        List<Message> myMessages = getMessages(userId);
        List<Response> iaResponses = getResponses(userId);

        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(myMessages);
        combinedList.addAll(iaResponses);

        int numberOfMessages = myMessages.size();

        combinedList.sort(Comparator.comparing(obj -> {
            if (obj instanceof Message) {
                return ((Message) obj).getTimestamp();
            } else {
                return ((Response) obj).getTimestamp();
            }
        }));
        StringBuilder historialBuilder = new StringBuilder();
        historialBuilder.append(CONTINUE_PROMPT);
        for (Object obj : combinedList) {
            if (obj instanceof Message) {
                historialBuilder.append("{'Turno':'Yo','Decision':'")
                        .append(((Message) obj).getMessage().replace("'", "\\'"))
                        .append("'},");
            } else if (obj instanceof Response) {
                historialBuilder.append("{'Turno':'Tú','Escena':'")
                        .append(((Response) obj).getResponse().replace("'", "\\'"))
                        .append("'},");
            }
        }
        if (historialBuilder.length() > 1) {
            historialBuilder.setLength(historialBuilder.length() - 1);
        }
        Prompt prompt = new Prompt();
        String ultimaPartePrompt = prompt.endPrompt(numberOfMessages);
        historialBuilder.append(ultimaPartePrompt).append(message.getMessage());
        String jsonPayload = historialBuilder.toString();
        ollamaService.callOllama(jsonPayload, user);
        messageRepository.save(message);
    }

    public List<Message> getMessages(UUID userId) {
        return messageRepository.findByUserId(userId);
    }

    public List<Response> getResponses(UUID userId) {
        return responseRepository.findByUserId(userId);
    }
}

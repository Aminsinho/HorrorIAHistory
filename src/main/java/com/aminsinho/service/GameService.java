package com.aminsinho.service;

import com.aminsinho.iservice.GameServiceInterface;
import com.aminsinho.models.*;
import com.aminsinho.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService implements GameServiceInterface {
    private final GameRepository gameSessionRepository;
    private final MessageRepository messageRepository;
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;

    public GameSession startGame(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        GameSession session = new GameSession(null, "Estado inicial...", user);
        return gameSessionRepository.save(session);
    }



    private GameSession getGameSession(UUID userId) {
        List<GameSession> sessions = gameSessionRepository.findByUserId(userId);

        if (sessions.isEmpty()) {
            throw new RuntimeException("Partida no encontrada");
        }

        GameSession session = sessions.get(0);
        return session;
    }


    @Transactional
    public Message sendMessage(UUID userId, Message message) {
        User user = getGameSession(userId).getUser();
        message.setUser(user);
        String responseText = callOllama(message.getMessage());
        Response responseMessage = new Response();
        responseMessage.setResponse(responseText);
        responseMessage.setUser(user);
        responseRepository.save(responseMessage);
        return messageRepository.save(message);
    }

    public List<Message> getMessages(UUID userId) {
        return messageRepository.findByUserId(userId);
    }

    public List<Response> getResponses(UUID userId) {
        return responseRepository.findByUserId(userId);
    }

    private String callOllama(String message) {
        StringBuilder responseBuilder = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://host.docker.internal:11434/api/generate");  // Cambiar a host.docker.internal
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = "{\"model\": \"llama3.2\", \"prompt\": \"" + message + "\"}";
            try (var os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    // Parse the response JSON and extract the "response" field
                    String response = responseLine.split("\"response\":\"")[1].split("\"")[0];
                    responseBuilder.append(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al llamar a Ollama API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return responseBuilder.toString();
    }
}

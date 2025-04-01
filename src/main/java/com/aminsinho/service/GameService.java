package com.aminsinho.service;

import com.aminsinho.iservice.GameServiceInterface;
import com.aminsinho.models.*;
import com.aminsinho.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService implements GameServiceInterface {
    private final GameRepository gameSessionRepository;
    private final MessageRepository messageRepository;
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;

    @Transactional
    public GameSession startGame(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        GameSession session = new GameSession(null, "Estado inicial...", user);

        // Llamar a la IA
        String responseText = callOllama("""
                Genera una historia interactiva en la que el jugador se despierta en una ciudad destruida sin recordar cómo llegó ahí.
                No hay señales de vida, pero hay indicios de que alguien o algo lo está observando.
                El jugador debe explorar, encontrar suministros y tomar decisiones para sobrevivir.
                Hay amenazas como criaturas, trampas y estructuras inestables.
                Cada decisión puede llevarlo más cerca de la salida o de la muerte.
                Si muere, explícale qué salió mal y qué opción habría sido mejor.
                Usa descripciones inmersivas y genera una sensación de peligro constante.
                Comienza la historia describiendo el entorno y pregunta: "¿Qué haces?""");

        // Guardar la respuesta de la IA
        Response responseMessage = new Response();
        responseMessage.setResponse(responseText);
        responseMessage.setUser(user);
        responseRepository.save(responseMessage);
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

        // Combinar mensajes y respuestas en una lista unificada
        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(myMessages);
        combinedList.addAll(iaResponses);

        // Ordenar por timestamp
        combinedList.sort(Comparator.comparing(obj -> {
            if (obj instanceof Message) {
                return ((Message) obj).getTimestamp();
            } else {
                return ((Response) obj).getTimestamp();
            }
        }));

        // Construir historial en JSON
        StringBuilder historialBuilder = new StringBuilder();

        historialBuilder.append("Estamos jugando a un juego, en el que tu me describes un paisaje y yo a ti una decision en base a esa decision tu me respondes con una escena nueva, el objetivo es que yo me salve o que me muera dependiendo de las decisiones que yo tome." +
                "de momento estas son nuestras escenas y decisiones: [");

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

        // Eliminar la última coma y cerrar el array
        if (historialBuilder.length() > 1) {
            historialBuilder.setLength(historialBuilder.length() - 1);
        }
        historialBuilder.append("] ahora en base a nuestras decisiones y escenas tienes que seguir la historia." +
                " " + message.getMessage());

        // Crear la estructura final del JSON para la IA
        String jsonPayload = historialBuilder.toString();

        System.out.println("Mensaje enviado a la IA: " + jsonPayload);

        // Llamar a la IA
        String responseText = callOllama(jsonPayload);

        // Guardar el mensaje del usuario
        messageRepository.save(message);

        // Guardar la respuesta de la IA
        Response responseMessage = new Response();
        responseMessage.setResponse(responseText);
        responseMessage.setUser(user);
        responseRepository.save(responseMessage);
    }

    public List<Message> getMessages(UUID userId) {
        return messageRepository.findByUserId(userId);
    }

    public List<Response> getResponses(UUID userId) {
        return responseRepository.findByUserId(userId);
    }

    private static final String OLLAMA_URL = "http://host.docker.internal:11434/api/generate";

    public String callOllama(String message) {
        HttpURLConnection conn = null;
        try {
            // Crear conexión
            URL url = new URL(OLLAMA_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Crear JSON de manera segura usando Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(new OllamaRequest("llama3.2", message));

            System.out.println("JSON enviado: " + jsonInputString);

            // Enviar JSON al servidor
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del servidor
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error al llamar a Ollama API: Código " + status);
            }

            // Convertir respuesta a String
            StringBuilder responseBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    JSONObject jsonResponse = new JSONObject(responseLine);
                    responseBuilder.append(jsonResponse.optString("response", ""));
                }
            }

            return responseBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al llamar a Ollama API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    // Clase auxiliar para estructurar el JSON
    private static class OllamaRequest {
        public String model;
        public String prompt;

        public OllamaRequest(String model, String prompt) {
            this.model = model;
            this.prompt = prompt;
        }
    }}

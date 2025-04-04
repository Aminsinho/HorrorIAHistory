package com.aminsinho.service;

import com.aminsinho.models.OllamaRequest;
import com.aminsinho.models.Response;
import com.aminsinho.models.User;
import com.aminsinho.repository.ResponseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class OllamaService {

    private final ResponseRepository responseRepository;

    private static final String OLLAMA_URL = "http://host.docker.internal:11434/api/generate";

    public void callOllama(String message, User user) {
        HttpURLConnection conn = null;
        try {
            // Crear conexión
            URL url = new URL(OLLAMA_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(new OllamaRequest("llama3.2", message));

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error al llamar a Ollama API: Código " + status);
            }
            StringBuilder responseBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    JSONObject jsonResponse = new JSONObject(responseLine);
                    responseBuilder.append(jsonResponse.optString("response", ""));
                }
            }


            Response responseMessage = new Response();
            responseMessage.setResponse(responseBuilder.toString());
            responseMessage.setUser(user);
            responseRepository.save(responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al llamar a Ollama API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}

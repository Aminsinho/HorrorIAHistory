package com.aminsinho.controller;

import com.aminsinho.models.GameSession;
import com.aminsinho.models.Message;
import com.aminsinho.models.Response;
import com.aminsinho.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @CrossOrigin(origins = "http://localhost:3000") // Cambia esto a la URL de tu frontend
    @PostMapping("/start")
    public ResponseEntity<GameSession> startGame(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(gameService.startGame(userId));
    }

    @CrossOrigin(origins = "http://localhost:3000") // Cambia esto a la URL de tu frontend
    @PostMapping("/decision")
    public ResponseEntity<GameSession> makeDecision(HttpServletRequest request, @RequestParam String decision) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(gameService.makeDecision(userId, decision));
    }

    @CrossOrigin(origins = "http://localhost:3000") // Cambia esto a la URL de tu frontend
    @PostMapping("/rollback")
    public ResponseEntity<GameSession> rollback(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(gameService.rollback(userId));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/message")
    public ResponseEntity<Message> sendMessage(HttpServletRequest request, @RequestBody Message message) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(gameService.sendMessage(userId, message));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(gameService.getMessages(userId));
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/responses")
    public ResponseEntity<List<Response>> getResponses(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(gameService.getResponses(userId));
    }

}
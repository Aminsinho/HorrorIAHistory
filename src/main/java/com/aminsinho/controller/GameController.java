package com.aminsinho.controller;

import com.aminsinho.models.GameSession;
import com.aminsinho.models.Message;
import com.aminsinho.models.Response;
import com.aminsinho.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/start")
    public ResponseEntity<GameSession> startGame(HttpServletRequest request) {
        try {
            UUID userId = UUID.fromString((String) request.getAttribute("userId"));
            return ResponseEntity.ok(gameService.startGame(userId));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/message")
    public ResponseEntity<Message> sendMessage(HttpServletRequest request, @RequestBody Message message) {
        try {
            UUID userId = UUID.fromString((String) request.getAttribute("userId"));
            gameService.sendMessage(userId, message);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(HttpServletRequest request) {
        try {
            UUID userId = UUID.fromString((String) request.getAttribute("userId"));
            return ResponseEntity.ok(gameService.getMessages(userId));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/responses")
    public ResponseEntity<List<Response>> getResponses(HttpServletRequest request) {
        try {
            UUID userId = UUID.fromString((String) request.getAttribute("userId"));
            return ResponseEntity.ok(gameService.getResponses(userId));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
package com.aminsinho.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "game_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameSession {
    // Getters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String currentState;

    @ManyToOne
    private User user;


    public GameSession(UUID id, String currentState) {
        this.id = id;
        this.currentState = currentState;
    }
}




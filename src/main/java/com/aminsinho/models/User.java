package com.aminsinho.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @Version
    private Long version;

    @OneToOne
    @JoinColumn(name = "game_session_id", nullable = true)
    private GameSession gameSession;

    public User(UUID userId, String mail, String testuser, String password, Object o) {
        this.id = userId;
        this.email = mail;
        this.username = testuser;
        this.password = password;
    }
}
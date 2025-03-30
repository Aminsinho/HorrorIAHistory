package com.aminsinho.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "decisions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Decision {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private GameSession session;

    private String decisionTaken;
    private String previousState;
}
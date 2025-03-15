package com.aminsinho.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    // Getters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private String platform;
    private String description;
    private String imageUrl;


    public Game(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}




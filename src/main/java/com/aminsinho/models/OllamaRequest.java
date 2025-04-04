package com.aminsinho.models;

// Clase auxiliar para estructurar el JSON
public class OllamaRequest {
    public String model;
    public String prompt;

    public OllamaRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }
}
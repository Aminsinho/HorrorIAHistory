package com.aminsinho.service;

import com.aminsinho.iservice.RecommendationServiceInterface;
import com.aminsinho.models.Recommendation;

import java.util.List;

public class RecommendationService implements RecommendationServiceInterface {


    @Override
    public List<Recommendation> getAllRecommendation() {
        return List.of();
    }
}

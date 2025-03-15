package com.aminsinho.iservice;


import com.aminsinho.models.Game;
import com.aminsinho.models.Question;
import com.aminsinho.models.Recommendation;

import java.util.List;

public interface RecommendationServiceInterface {

    List<Recommendation> getAllRecommendation();
}

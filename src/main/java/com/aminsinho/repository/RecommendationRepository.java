package com.aminsinho.repository;

import com.aminsinho.models.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    boolean existsByGameId(Long gameId);
}

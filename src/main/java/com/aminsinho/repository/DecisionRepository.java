package com.aminsinho.repository;

import com.aminsinho.models.Decision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface DecisionRepository extends JpaRepository<Decision, Long> {
    List<Decision> findBySessionIdOrderByIdDesc(UUID sessionId);
}

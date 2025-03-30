package com.aminsinho.repository;

import com.aminsinho.models.Message;
import com.aminsinho.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ResponseRepository extends JpaRepository<Response, UUID> {

    List<Response> findByUserId(UUID userId);
}
package com.aminsinho.repository;

import com.aminsinho.models.Message;
import com.aminsinho.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    List<Message> findByUserId(UUID userId);
}
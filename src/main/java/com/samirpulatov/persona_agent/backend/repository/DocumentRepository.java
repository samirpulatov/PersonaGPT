package com.samirpulatov.persona_agent.backend.repository;

import com.samirpulatov.persona_agent.backend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document,Integer> {
    boolean existsByUserId(Integer userId);
}

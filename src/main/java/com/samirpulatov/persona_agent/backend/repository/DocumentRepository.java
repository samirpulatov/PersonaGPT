package com.samirpulatov.persona_agent.backend.repository;

import com.samirpulatov.persona_agent.backend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Integer> {
}

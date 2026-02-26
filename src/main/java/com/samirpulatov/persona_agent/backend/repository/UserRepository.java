package com.samirpulatov.persona_agent.backend.repository;

import com.samirpulatov.persona_agent.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}

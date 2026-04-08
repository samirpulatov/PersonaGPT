package com.samirpulatov.persona_agent.backend.dto;

public record UserLoginResponse (
        String token,
        String email,
        String username,
        String accountType,
        String role
) {
}

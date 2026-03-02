package com.samirpulatov.persona_agent.backend.dto;

public record UserLoginForm(
        String email,
        String username,
        String password
) { }

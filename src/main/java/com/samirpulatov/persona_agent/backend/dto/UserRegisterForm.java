package com.samirpulatov.persona_agent.backend.dto;

public record UserRegisterForm (
    String firstName,
    String lastName,
    String username,
    String email,
    String password,
    String role
) {}

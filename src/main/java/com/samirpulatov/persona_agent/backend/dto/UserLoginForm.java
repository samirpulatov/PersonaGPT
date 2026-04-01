package com.samirpulatov.persona_agent.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginForm(
       @NotBlank(message = "Email or username is required")
        String login,

       @NotBlank(message = "Password is required")
       String password
) { }

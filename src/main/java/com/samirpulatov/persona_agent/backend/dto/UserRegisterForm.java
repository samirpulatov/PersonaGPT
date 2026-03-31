package com.samirpulatov.persona_agent.backend.dto;

import com.samirpulatov.persona_agent.backend.enums.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterForm (
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String username,
    @Email @NotBlank String email,
    @NotBlank String password,
    @NotNull AccountType accountType
) {}

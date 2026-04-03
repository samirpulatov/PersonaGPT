package com.samirpulatov.persona_agent.backend.dto;

import com.samirpulatov.persona_agent.backend.enums.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterForm (
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    String lastName,

    @NotNull(message = "Please select an account type")
    AccountType accountType,

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    String email,

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 15, message = "Password must be between 6 and 15 characters")
    String password
) {}

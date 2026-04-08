package com.samirpulatov.persona_agent.backend.entity;

import com.samirpulatov.persona_agent.backend.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Lastname is mandatory")
    private String lastName;

    @Column(nullable = false)
    @NotBlank(message = "Please select your account type")
    private String accountType;


    @Enumerated(EnumType.STRING)
    private Role role;


    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is mandatory")
    private String password;


}

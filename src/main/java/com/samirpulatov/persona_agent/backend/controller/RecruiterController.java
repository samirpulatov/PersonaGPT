package com.samirpulatov.persona_agent.backend.controller;

import com.samirpulatov.persona_agent.backend.service.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "id", userDetails.getId(),
                "username", userDetails.getRealUserName(),
                "email", userDetails.getUsername(),
                "role", userDetails.getRole(),
                "accountType", userDetails.getAccountType()
        ));
    }
}

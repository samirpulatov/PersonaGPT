package com.samirpulatov.persona_agent.backend.controller;

import com.samirpulatov.persona_agent.backend.dto.UserLoginForm;
import com.samirpulatov.persona_agent.backend.dto.UserLoginResponse;
import com.samirpulatov.persona_agent.backend.dto.UserRegisterForm;
import com.samirpulatov.persona_agent.backend.service.CustomUserDetails;
import com.samirpulatov.persona_agent.backend.service.JwtService;
import com.samirpulatov.persona_agent.backend.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterForm form) {

        boolean created = userService.registerUser(
                form.firstName(),
                form.lastName(),
                form.accountType(),
                form.email(),
                form.username(),
                form.password()
        );
        if (!created) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("conflict","A user with this email or username already exists"));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message","User registered successfully"));
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginForm form) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.login(), form.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        UserLoginResponse userLoginResponse = new UserLoginResponse(
                token,
                userDetails.getUsername(),
                userDetails.getRealUserName(),
                userDetails.getAccountType(),
                userDetails.getRole()
        );

        return ResponseEntity.ok(userLoginResponse);
    }
}

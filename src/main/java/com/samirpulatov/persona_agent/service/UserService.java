package com.samirpulatov.persona_agent.service;

import com.samirpulatov.persona_agent.entity.User;
import com.samirpulatov.persona_agent.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(String username,String rawPassword) {
        return userRepository.findByUsername(username)
                .map(user -> "User already exists")
                .orElseGet(() -> {
                    User user = User.builder()
                            .username(username)
                            .password(passwordEncoder.encode(rawPassword))
                            .build();
                    userRepository.save(user);
                    return "User registered successfully";
                });
    }
}

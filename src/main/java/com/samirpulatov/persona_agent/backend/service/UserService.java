package com.samirpulatov.persona_agent.backend.service;

import com.samirpulatov.persona_agent.backend.entity.User;
import com.samirpulatov.persona_agent.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(
            String firstName,
            String lastName,
            String role,
            String email,
            String username,
            String rawPassword) {

        if(userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            return false;
        }


        // create and save a user
        User user = User.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .role(role)
                            .email(email)
                            .username(username)
                            .password(passwordEncoder.encode(rawPassword))
                            .build();
        userRepository.save(user);
        return true;

    }
}

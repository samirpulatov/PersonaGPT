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

    public String registerUser(String firstName, String lastName, String email, String rawPassword, Model model) {

        if(checkUser(firstName,lastName,email,rawPassword)) {
            return "You have an existing account with " + email;
        }
        // create and save a user
        User user = User.builder()
                            .username(lastName)
                            .password(passwordEncoder.encode(rawPassword))
                            .build();
        userRepository.save(user);
        return "User registered successfully";

    }

    private boolean checkUser(String firstName,String lastName,String email,String rawPassword) {
        return userRepository.findByUsername(email).isPresent(); //check if a user exists or not
    }
}

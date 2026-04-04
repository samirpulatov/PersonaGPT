package com.samirpulatov.persona_agent.backend.service;

import com.samirpulatov.persona_agent.backend.entity.User;
import com.samirpulatov.persona_agent.backend.enums.AccountType;
import com.samirpulatov.persona_agent.backend.enums.Role;
import com.samirpulatov.persona_agent.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
            AccountType accountType,
            String email,
            String username,
            String rawPassword) {

        // remove leading and trailing spaces and convert to lowercase
        String firstNameTrimmed = firstName.trim();
        String lastNameTrimmed = lastName.trim();
        String normalizedEmail = email.trim().toLowerCase();
        String normalizedUsername = username.trim().toLowerCase();


        // check if a user already exists
        if(userExists(normalizedUsername, normalizedEmail)) {
            return false;
        }

        // create and save a user
        User user = User.builder()
                            .firstName(firstNameTrimmed)
                            .lastName(lastNameTrimmed)
                            .accountType(accountType.name())
                            .role(Role.USER)
                            .email(normalizedEmail)
                            .username(normalizedUsername)
                            .password(passwordEncoder.encode(rawPassword))
                            .build();
        userRepository.save(user);
        return true;

    }

    public boolean userExists(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
    }

    public boolean authenticateUser(String login, String password)  {
        Optional<User> user = login.contains("@") ? userRepository.findByEmail(login) : userRepository.findByUsername(login);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    public User findByLogin(String login) {
        return userRepository.findByEmail(login)
                .or(()->userRepository.findByUsername(login))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}

package com.samirpulatov.persona_agent.backend.controller;

import com.samirpulatov.persona_agent.backend.dto.UserRegisterForm;
import com.samirpulatov.persona_agent.backend.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterForm form, Model model) {
        return userService.registerUser(form.firstName(), form.lastName(), form.email(),form.password(),model);
    }

}

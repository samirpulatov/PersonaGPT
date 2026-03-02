package com.samirpulatov.persona_agent.backend.controller;

import com.samirpulatov.persona_agent.backend.dto.UserRegisterForm;
import com.samirpulatov.persona_agent.backend.service.UserService;

import org.springframework.dao.DataIntegrityViolationException;
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

    //Create a new user or return an error message if a user already exists
    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterForm form, Model model) {
        try {
            boolean created = userService.registerUser(
                    form.firstName(),
                    form.lastName(),
                    form.role(),
                    form.email(),
                    form.username(),
                    form.password()
            );

            if (!created) {
                model.addAttribute("errorMessage", "A user with this email or username already exists.");
                model.addAttribute("form", form);
                return "sign_up";
            }

            return "redirect:/signin?registered";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "A user with this email or username already exists.");
            model.addAttribute("form", form);
            return "sign_up";
        }


    }

}

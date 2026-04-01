package com.samirpulatov.persona_agent.backend.controller;

import com.samirpulatov.persona_agent.backend.dto.UserLoginForm;
import com.samirpulatov.persona_agent.backend.dto.UserRegisterForm;
import com.samirpulatov.persona_agent.backend.service.UserService;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Method called when the user visits the sign-in page to display the login form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("form", new UserRegisterForm("","","","","",null));
        return "sign_up";
    }

    //Create a new user or return an error message if a user already exists
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") UserRegisterForm form,
                           BindingResult bindingResult,
                           Model model) {

        // Check if the form is valid or contains errors
        if(bindingResult.hasErrors()) {
           model.addAttribute("form", removePasswordFromRegisterForm(form));
           return "sign_up";
        }


        try {
            boolean created = userService.registerUser(
                    form.firstName(),
                    form.lastName(),
                    form.accountType(),
                    form.email(),
                    form.username(),
                    form.password()
            );


            if (!created) {
                model.addAttribute("errorMessage", "A user with this email or username already exists.");
                model.addAttribute("form", removePasswordFromRegisterForm(form));
                return "sign_up";
            }

            return "redirect:/auth/login";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Could not register user. Email or username may already be in use.");
            model.addAttribute("form", removePasswordFromRegisterForm(form));
            return "sign_up";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginForm form,Model model) {

        boolean userExists = userService.userExists(form.username(),form.email());

        if(!userExists) {
            model.addAttribute("errorMessage", "A user with this email or username does not exist. Please register first.");
            return "sign_in";
        }

        return "redirect:/welcome";

    }

    //Removes the password before sending the form back to the frontend
    private UserRegisterForm removePasswordFromRegisterForm(UserRegisterForm form) {
        return new UserRegisterForm(
                form.firstName(),
                form.lastName(),
                form.username(),
                form.email(),
                "",
                form.accountType()
        );
    }
}

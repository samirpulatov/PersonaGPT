package com.samirpulatov.persona_agent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/sign_up")
    public String signUpPage() {
        return "sign_up";
    }

}

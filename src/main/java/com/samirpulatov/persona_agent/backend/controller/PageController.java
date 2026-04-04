package com.samirpulatov.persona_agent.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Controller for mapping static pages
@Controller
public class PageController {

    //Forward to the static HTML pages without the .html extension
    @GetMapping("/sign_in")
    public String signIn() {
        return "forward:/sign_in.html";
    }

    @GetMapping("/sign_up")
    public String signUp() {
        return "forward:/sign_up.html";
    }
}

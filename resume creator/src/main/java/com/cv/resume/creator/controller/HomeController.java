package com.cv.resume.creator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "login"; // This should match the name of your login HTML file without the extension
    }
    
    @GetMapping("/home")
    public String home() {
        return "home"; // This should match the name of your home HTML file without the extension
    }
}
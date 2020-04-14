package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
  
    @GetMapping("/login")
    public String showLoginForm(){
        return "users/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/sign-up";
    }

    @PostMapping("/logout")
    public String showLogoutForm(){
        return "redirect:/login";

    }
}

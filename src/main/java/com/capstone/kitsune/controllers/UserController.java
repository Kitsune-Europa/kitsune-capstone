package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private UserRepo users;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepo users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    //sign-up form
    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    //Saving user
    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        boolean debug = passwordEncoder.matches(user.getPassword(), hash);

        user.setPassword(hash);
        users.save(user);
        return "redirect:/login";
    }

    //navigating to home page


    @GetMapping("/account/edit")
    public String showAccountEditForm(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByid(loggedInUser.getId());
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/account/edit")
    public String accountEdit(@RequestParam String password, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByid(loggedInUser.getId());
        String hash = this.passwordEncoder.encode(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(hash);
        users.save(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/account")
    public String account(Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByid(loggedInUser.getId());
        model.addAttribute("user", user);
<<<<<<< HEAD
        return "profile";
=======
        return "users/account";

>>>>>>> c9bcca4180f1710e7d3cdc2810bc41fa56b1a9c2
    }
}


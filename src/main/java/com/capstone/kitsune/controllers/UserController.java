package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @GetMapping("/account/{username}/edit")
    public String showAccountEditForm(Model model, @PathVariable String username) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            User user = users.findByUsername(username);

            model.addAttribute("user", user);
            return "users/edit";
        } else {
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/account/(username)/edit")
    public String accountEdit(@PathVariable String username, @RequestParam String password, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String profile_picture) {
        User user = users.findByUsername(username);
        String hash = this.passwordEncoder.encode(password);
        boolean debug = this.passwordEncoder.matches(user.getPassword(), hash);
        user.setProfilePicture(profile_picture);
        user.setPassword(hash);
        user.setEmail(email);
        user.setLastName(lastName);
        users.save(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/account")
    public String showAccountForm(Model model) {
        // Getting logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User loggedIn = users.findByUsername(name);

        if (loggedIn != null) {
            // Setting username based on principal
            model.addAttribute("user", loggedIn);
            return "users/account";
        }
        else {
            return "redirect:/login";
        }
    }

    //Saving the user profile photo to the database
    @PostMapping("/account")
    public String postProfilePhoto(@RequestParam(value = "profilePicture") String profilePicture) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.getOne(loggedInUser.getId());
        //Set Profile Photo
        user.setProfilePicture(profilePicture);
        //Save user
        users.save(user);
        return "redirect:/dashboard";

    }



//    @PostMapping(value = "/account")
//    public String postProfilePhoto(@RequestParam(value="profilePicture", required = false) String profilePicture) {
//        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = users.findByUsername(loggedInUser.toString());
//        user.setProfilePicture(profilePicture);
//        users.save(user);
//        return "redirect:/dashboard";
//
//    }
}


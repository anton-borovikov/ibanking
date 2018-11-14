package com.example.ibanking.controller;

import com.example.ibanking.domain.User;
import com.example.ibanking.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile")
    public String updateUserProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String userEmail,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Integer age
    ) {
        userService.updateUserProfile(user, userEmail, firstName, lastName, age);
        return "redirect:/user/profile";
    }

    @PostMapping("/resendCode")
    public String reSendActivationCode(@AuthenticationPrincipal User user) {
        userService.reSendActivationCode(user);
        return "redirect:/user/profile";
    }

    @GetMapping("/activation")
    public String activationPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);

        return "activation";
    }
}

package com.example.ibanking.controller;

import com.example.ibanking.domain.CaptureResponse;
import com.example.ibanking.domain.User;
import com.example.ibanking.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    private UserService userService;
    private RestTemplate restTemplate;

    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("password2") String passConfirm,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptureResponse response = restTemplate.postForObject(url, Collections.emptyList(), CaptureResponse.class);
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill the captcha");
        }

        if (StringUtils.isEmpty(passConfirm)) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty!");
        }

        boolean passNotEqual = user.getPassword() != null && !user.getPassword().equals(passConfirm);
        if (passNotEqual) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (StringUtils.isEmpty(passConfirm) || bindingResult.hasErrors()) {
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage)
            );
            model.mergeAttributes(errorsMap);
        }

        if (StringUtils.isEmpty(passConfirm) || passNotEqual || bindingResult.hasErrors() || !response.isSuccess()) {
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("message", user.getUsername() + " already exists!");
            model.addAttribute("message_type", "warning");
            return "registration";
        }

        model.addAttribute("message", user.getUsername() + "User was created successfully!");
        model.addAttribute("message_type", "success");

        redirectAttributes.addFlashAttribute("message", "User " + user.getUsername() + " was created successfully!");
        redirectAttributes.addFlashAttribute("message_type", "success");

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(Model model, @PathVariable String code) {
        boolean isAcivated = userService.activateUser(code);

        if (isAcivated) {
            model.addAttribute("message", "User successfully activated! Please reentry!");
            model.addAttribute("message_type", "success");
        } else {
            model.addAttribute("message", "Activation code is not found!");
            model.addAttribute("message_type", "warning");
        }
        return "login";
    }
}

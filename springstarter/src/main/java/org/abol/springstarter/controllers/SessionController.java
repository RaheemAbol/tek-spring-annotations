package org.abol.springstarter.controllers;

import org.abol.springstarter.models.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@SessionAttributes("baseUser")
@RequestMapping("/session")
public class SessionController {

    @ModelAttribute("baseUser")
    public BaseUser baseUser() {
        return new BaseUser();
    }

    @GetMapping("/registerForm")
    public String showForm(Model model) {
        log.info("Displaying registration form");
        model.addAttribute("baseUser", new BaseUser());
        return "register-session";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("baseUser") BaseUser baseUser, Model model) {
        log.info("User saved: {}", baseUser);
        model.addAttribute("message", "User successfully saved in session!");
        return "redirect:/session/userDetails";
    }

    @GetMapping("/userDetails")
    public String baseUserDetails(@SessionAttribute("baseUser") BaseUser baseUser, Model model) {
        log.info("Displaying details for user: {}", baseUser);
        model.addAttribute("baseUser", baseUser);
        return "user-details-session";
    }

    @GetMapping("/clearSession")
    public String clearSession(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session cleared");
        return "redirect:/session/registerForm";
    }
}

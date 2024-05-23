package org.abol.springstarter.controllers;

import org.abol.springstarter.models.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class UserController {

    private List<BaseUser> users = new ArrayList<>();

    @GetMapping("/registerForm")
    public String showForm(Model model) {
        log.info("Displaying registration form");
        model.addAttribute("baseUser", new BaseUser());
        return "register-form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("baseUser") BaseUser baseUser, Model model) {
        users.add(baseUser);
        log.info("User saved: {}", baseUser);
        model.addAttribute("baseUser", baseUser);
        return "userDetails";
    }

    @GetMapping("/userDetails")
    public String baseUserDetails(@RequestParam(name="index", required=false) Integer index, Model model) {
        if (index != null && index < users.size()) {
            model.addAttribute("baseUser", users.get(index));
            log.info("Displaying details for user at index {}", index);
        } else {
            model.addAttribute("baseUser", new BaseUser());
            log.warn("No user found at index {}", index);
        }
        return "userDetails";
    }

    @GetMapping("/allUsers")
    public String displayAll(Model model) {
        log.info("Displaying all users");
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/editUser")
    public String editUserForm(@RequestParam("index") int index, Model model) {
        if (index >= 0 && index < users.size()) {
            model.addAttribute("baseUser", users.get(index));
            model.addAttribute("index", index);
            return "edit-user-form";
        } else {
            return "redirect:/allUsers";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("baseUser") BaseUser baseUser, @RequestParam("index") int index, Model model) {
        if (index >= 0 && index < users.size()) {
            users.set(index, baseUser);
            log.info("User updated: {}", baseUser);
        }
        model.addAttribute("users", users);
        return "redirect:/allUsers";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("index") int index, Model model) {
        if (index >= 0 && index < users.size()) {
            log.info("Deleting user at index {}", index);
            users.remove(index);
        }
        model.addAttribute("users", users);
        return "redirect:/allUsers";
    }

    // Example using @RequestParam
    @GetMapping("/getUserByEmail")
    public String getUserByEmail(@RequestParam("email") String email, Model model) {
        for (BaseUser user : users) {
            if (user.getEmail().equals(email)) {
                model.addAttribute("baseUser", user);
                log.info("Displaying details for user with email {}", email);
                return "userDetails";
            }
        }
        model.addAttribute("baseUser", new BaseUser());
        log.warn("No user found with email {}", email);
        return "userDetails";
    }

    // Example using @PathVariable only
    @GetMapping("/getUserById/{id}")
    public String getUserByIdPath(@PathVariable("id") int id, Model model) {
        if (id >= 0 && id < users.size()) {
            model.addAttribute("baseUser", users.get(id));
            log.info("Displaying details for user with ID {}", id);
            return "userDetails";
        } else {
            model.addAttribute("baseUser", new BaseUser());
            log.warn("No user found with ID {}", id);
            return "userDetails";
        }
    }
}

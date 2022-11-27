package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public String showProfile(Model model, @AuthenticationPrincipal User userEntity) {
        model.addAttribute("user", userEntity);
        return "user";
    }

    @GetMapping("/{id}")
    public String showProfileFromAdmin(Model model, @PathVariable Long id) {
        User user = userService.getUser(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "user";
        } else {
            model.addAttribute("messages", Arrays.asList("Нет такого пользователя.", "Вы не угадали ID'шник ))"));
            return "user_list";
        }
    }

}





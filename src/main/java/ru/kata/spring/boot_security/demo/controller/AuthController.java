package ru.kata.spring.boot_security.demo.controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    public AuthController(PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registrationAdmin(@ModelAttribute("user") User user,Model model){
        model.addAttribute("listOfRoles", roleService.listRoles());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(User formUser, Model model) {
        String err = "Пароли не совпадают";
        User user = new User();
        user.setUsername(formUser.getUsername());
        user.setPassword(passwordEncoder.encode(formUser.getPassword()));
        user.setEmail(formUser.getEmail());
        user.setRoles(formUser.getRoles());
        if (formUser.getPassword().equals(formUser.getConfirm())) {
            if (userService.loadUserByUsername(user.getUsername()) == null) {
                userService.createOrUpdate(user);
                return "redirect:/login";
            }
            err = "Логин занят";
        }
        model.addAttribute("error", err);
        return "register";
    }
}

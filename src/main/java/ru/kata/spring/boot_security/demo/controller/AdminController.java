package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    /***
     * Подготовить объект User для сохранения в базу
     */
    @GetMapping("/newUser")
    public String createForm(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("listOfRoles", roleService.getRoles());
        return "user-create";
    }

    /***
     * Сохранить в базу
     */
    @PostMapping
    public String createUser(@ModelAttribute("newUser")@Valid User user, BindingResult bindingResult) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        if(bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.createOrUpdate(user);
        return "redirect:/admin";
    }
    /***
     * Получить всех пользователей
     */
    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "user_list";
    }

    /***
     * Получить одного пользователя
     */
    @GetMapping("/{id}")
    public String getUserById(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }
    /***
     * Подготовить изменения для объекта User
     */
    @GetMapping("/{id}/edit")
    public String editForm(Model model, @PathVariable(name = "id") Long id) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", roleService.getRoles());
        return "user-update";
    }

    /***
     * Сохранить изменённого пользователя
     */
    @PatchMapping()
    public String editUser(@RequestParam("listRoles") Collection<Role> roles, @ModelAttribute("user") User user) {
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setRoles(roles);
        userService.createOrUpdate(user);
        return "redirect:/admin";
    }
    /***
     * Удалить пользователя (подготовки объекта User не требуется)
     */
    @DeleteMapping()
    public String deleteUserById(@Param("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

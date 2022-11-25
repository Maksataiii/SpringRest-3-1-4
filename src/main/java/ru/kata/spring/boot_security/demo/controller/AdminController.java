package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping(params = "logout")
    public String logout() {
        return "redirect:/logout";
    }

    //--- CREATE

    /***
     * Подготовить объект User для сохранения в базу
     */
    @GetMapping("/newUser")
    public String createForm(Model model, @ModelAttribute("user") User user) {
        List<Role> listRoles = roleService.getRoles();
        model.addAttribute("listRoles", listRoles);
        return "user-create";
    }

    /***
     * Сохранить в базу
     */
//    @PostMapping
//    public String createUser(@ModelAttribute("user") User user) {
////        user.setRoles(Collections.singleton(roleService.createRole("USER")));
////        user.setPassword(passwordEncoder.encode("0000"));
////        user.setUsername(user.getUsername().toLowerCase());
//        userService.createOrUpdate(user);
//        if (userService.loadUserByUsername(user.getUsername()) == null) {
//            userService.createOrUpdate(user);
//            return "redirect:/admin";
//        } else {
//            return "redirect:/admin/new?error=login is exists";
//        }
//    }
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

    //--- READ

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

    //--- UPDATE

    /***
     * Подготовить изменения для объекта User
     */
    @GetMapping("/{id}/edit")
    public String editForm(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("listRoles", roleService.getRoles());
        return "user-update";
    }

    /***
     * Сохранить изменённого пользователя
     */
    @PatchMapping()
    public String editUser(@ModelAttribute("user") User user) {
        User oldUser = userService.getUser(user.getId());
        user.setUsername(oldUser.getUsername());
        user.setPassword(oldUser.getPassword());
        user.setRoles(oldUser.getRoles());
        userService.createOrUpdate(user);
        return "redirect:/admin";
    }

    //--- DELETE

    /***
     * Удалить пользователя (подготовки объекта User не требуется)
     */
    @DeleteMapping()
    public String deleteUserById(Model model, @Param("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

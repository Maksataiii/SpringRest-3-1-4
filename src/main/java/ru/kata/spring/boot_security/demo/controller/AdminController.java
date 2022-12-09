package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.Collection;

@RestController
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
     * Сохранить в базу
     */
    @PostMapping("/admin")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createOrUpdate(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.listRoles(), HttpStatus.OK);
    }
    /***
     * Получить всех пользователей
     */
    @GetMapping("/admin")
    public ResponseEntity<Collection<User>> adminPage() {
        final Collection<User> users = userService.getUsersList();
        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /***
     * Получить одного пользователя
     */
    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
    }
    /***
     * Сохранить изменённого пользователя
     */
    @PatchMapping("/admin/{id}")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        userService.createOrUpdate(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    /***
     * Удалить пользователя (подготовки объекта User не требуется)
     */
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}

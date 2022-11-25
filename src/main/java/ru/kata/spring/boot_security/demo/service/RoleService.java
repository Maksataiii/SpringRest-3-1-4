package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;


@Service
@Transactional

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(String role) {
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        Role entity = roleRepository.findRoleByRolename(role);
        if (entity == null) {
            entity = roleRepository.save(new Role(role));
        }
        return entity;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

}

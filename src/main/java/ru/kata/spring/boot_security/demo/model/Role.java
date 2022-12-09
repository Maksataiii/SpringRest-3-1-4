package ru.kata.spring.boot_security.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rolename", unique = true)
    private String rolename;
    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    @JsonIgnore
    private List< User > users;

    public Role() {
    }
    public Role(String role) {
    if (!role.startsWith("ROLE_")) {
        role = "ROLE_" + role;
    }
    setRolename(role);
    }

    @Override
    public String getAuthority() {
        return getRolename();
    }
}

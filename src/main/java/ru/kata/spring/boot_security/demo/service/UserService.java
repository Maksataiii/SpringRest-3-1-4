package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.ArrayList;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserEntityByUsername(username);
        return user;
    }

//
    @Transactional
    public void createOrUpdate(User user) {
        User old = userRepository.findUserEntityByUsername(user.getUsername());
        if (old != null) user.setId(old.getId());
        userRepository.save(user);
    }
    public Collection<User> getList() {
        Collection<User> list = new ArrayList<>();
        userRepository.findAll().forEach(list::add);
        return list;
    }

    /***
     * Получить пользователя по ID
     * @param id ID пользователя
     * @return пользователь либо null
     */
    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}

package com.danieldev.authsystem.service;

import com.danieldev.authsystem.entity.Role;
import com.danieldev.authsystem.entity.User;
import com.danieldev.authsystem.entity.enums.RoleName;
import com.danieldev.authsystem.exception.CustomException;
import com.danieldev.authsystem.repository.RoleRepository;
import com.danieldev.authsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuário não encontrado", HttpStatus.NOT_FOUND));
    }

    public User createUser(String username, String password, RoleName roleName) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new CustomException("Usuário já existe", HttpStatus.BAD_REQUEST);
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new CustomException("Role não encontrada", HttpStatus.NOT_FOUND));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(role));

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
    }
}

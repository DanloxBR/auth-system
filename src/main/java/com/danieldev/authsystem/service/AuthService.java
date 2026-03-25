package com.danieldev.authsystem.service;

import com.danieldev.authsystem.entity.Role;
import com.danieldev.authsystem.entity.User;
import com.danieldev.authsystem.entity.enums.RoleName;
import com.danieldev.authsystem.repository.RoleRepository;
import com.danieldev.authsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
    }
}

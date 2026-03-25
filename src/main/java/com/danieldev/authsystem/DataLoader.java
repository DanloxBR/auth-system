package com.danieldev.authsystem;

import com.danieldev.authsystem.entity.Role;
import com.danieldev.authsystem.entity.User;
import com.danieldev.authsystem.entity.enums.RoleName;
import com.danieldev.authsystem.repository.RoleRepository;
import com.danieldev.authsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(RoleName.ROLE_USER);
                    return roleRepository.save(role);
                });

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(RoleName.ROLE_ADMIN);
                    return roleRepository.save(role);
                });

        if (!userRepository.findByUsername("admin").isPresent()) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Collections.singleton(adminRole));

            userRepository.save(admin);

            System.out.println("ADMIN criado: admin / admin123");
        }
    }
}
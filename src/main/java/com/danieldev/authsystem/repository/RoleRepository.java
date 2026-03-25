package com.danieldev.authsystem.repository;

import com.danieldev.authsystem.entity.Role;
import com.danieldev.authsystem.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}

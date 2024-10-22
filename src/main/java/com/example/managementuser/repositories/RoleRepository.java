package com.example.managementuser.repositories;


import com.example.managementuser.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String emailOrUserName);
    Optional<Role> findByUserId(Long userId);
}

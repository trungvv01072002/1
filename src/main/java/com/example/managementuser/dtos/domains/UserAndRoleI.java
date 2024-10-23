package com.example.managementuser.dtos.domains;

import com.example.managementuser.enums.RoleEnum;

public interface UserAndRoleI {
    Long getId();
    String getFullName();
    String getEmail();
    String getUsername();
    RoleEnum getName();
}

package com.example.managementuser.dtos.domains;

import com.example.managementuser.constants.RoleEnum;

public interface UserAndRoleI {
    Long getId();
    String getFullName();
    String getEmail();
    String getUsername();
    RoleEnum getName();
}

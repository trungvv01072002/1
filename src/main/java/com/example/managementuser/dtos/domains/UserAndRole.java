package com.example.managementuser.dtos.domains;

import com.example.managementuser.constants.RoleEnum;
import lombok.*;

@Getter
@Setter
@Builder
public class UserAndRole {
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private RoleEnum role;

    public UserAndRole(Long id, String fullName, String email, String username, RoleEnum role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.role = role;
    }

}

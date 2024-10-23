package com.example.managementuser.dtos.res;

import com.example.managementuser.entities.Role;
import com.example.managementuser.enums.RoleEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {
    private Long id;
    private String fullName;
    private String email;
    private String userName;

    private List<Role> roles;
}

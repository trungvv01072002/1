package com.example.managementuser.dtos.res;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private String role;
    private String password;
}

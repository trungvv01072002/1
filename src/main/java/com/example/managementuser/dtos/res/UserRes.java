package com.example.managementuser.dtos.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRes {
    private Long id;
    private String name;
    private String email;
    private String password;
}

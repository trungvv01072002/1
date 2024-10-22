package com.example.managementuser.dtos.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserLoginReq {
    private String email;
    private String password;
}

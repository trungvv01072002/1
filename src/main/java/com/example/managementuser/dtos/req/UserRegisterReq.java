package com.example.managementuser.dtos.req;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserRegisterReq {
    private String fullName;
    private String userName;
    private String email;
    private String password;

}

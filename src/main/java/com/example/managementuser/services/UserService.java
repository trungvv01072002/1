package com.example.managementuser.services;

import com.example.managementuser.dtos.domains.UserAndRole;
import com.example.managementuser.dtos.req.JWTAuthDto;
import com.example.managementuser.dtos.req.UserLoginReq;
import com.example.managementuser.dtos.req.UserRegisterReq;
import com.example.managementuser.dtos.res.UserRes;
import com.example.managementuser.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    JWTAuthDto registerUser(UserRegisterReq userRegisterReq) throws Exception;

    UserRes loginUser(UserLoginReq userLoginReq);

    Page<UserRes> getAllUser(int page, int size, String keySearch);

    UserRes updateUser(UserRegisterReq userRegisterReq, Long id);

    UserAndRole getUserByJwt(String jwt);
}

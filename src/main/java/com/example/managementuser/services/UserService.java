package com.example.managementuser.services;

import com.example.managementuser.dtos.req.UserLoginReq;
import com.example.managementuser.dtos.req.UserRegisterReq;
import com.example.managementuser.dtos.res.UserRes;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRes registerUser(UserRegisterReq userRegisterReq) throws Exception;

    UserRes loginUser(UserLoginReq userLoginReq);

    Page<UserRes> getAllUser(int page, int size, String keySearch);




}

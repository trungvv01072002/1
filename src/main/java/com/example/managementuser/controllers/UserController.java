package com.example.managementuser.controllers;

import com.example.managementuser.dtos.req.JWTAuthDto;
import com.example.managementuser.dtos.req.UserLoginReq;
import com.example.managementuser.dtos.req.UserRegisterReq;
import com.example.managementuser.dtos.res.UserRes;
import com.example.managementuser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterReq userRegisterReq) {
        try{

            UserRes userRes = userService.registerUser(userRegisterReq);
            return new ResponseEntity<>(userRes, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginReq userLoginReq) {
        try{
            UserRes userRes = userService.loginUser(userLoginReq);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/search")
    public ResponseEntity<?> search(@RequestParam int page, @RequestParam int size, @RequestParam String keySearch) {
        try{
            return new ResponseEntity<>(userService.getAllUser(page, size, keySearch), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}

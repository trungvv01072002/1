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
            return new ResponseEntity<>( userService.registerUser(userRegisterReq), HttpStatus.CREATED);
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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody UserRegisterReq userRegisterReq, @PathVariable("id") Long id) {
        try{
            UserRes userRes = userService.updateUser(userRegisterReq, id);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserByJwt(@RequestHeader("Authorization") String jwt) {
        try{
            return new ResponseEntity<>(userService.getUserByJwt(jwt), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}

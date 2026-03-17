package com.example.to_do.controller;

import com.example.to_do.DTO.LoginRequestDto;
import com.example.to_do.DTO.LoginResponse;
import com.example.to_do.DTO.RegisterRequestDto;
import com.example.to_do.entity.User;
import com.example.to_do.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    private ResponseEntity<User> register(@RequestBody RegisterRequestDto registerRequestDto){
        return ResponseEntity.ok(userService.register(registerRequestDto));
    }

    @PostMapping("/login")
    private ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }
}

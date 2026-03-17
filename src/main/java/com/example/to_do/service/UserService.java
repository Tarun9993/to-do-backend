package com.example.to_do.service;

import com.example.to_do.DTO.LoginRequestDto;
import com.example.to_do.DTO.LoginResponse;
import com.example.to_do.DTO.RegisterRequestDto;
import com.example.to_do.JWT.JwtService;
import com.example.to_do.MapStruct.UserMapper;
import com.example.to_do.entity.Priority;
import com.example.to_do.entity.User;
import com.example.to_do.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserMapper userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequestDto registerRequestDto) {
        registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        return userRepository.save(userMapper.convertToEntity(registerRequestDto));
    }

    public LoginResponse login(LoginRequestDto loginRequestDto) {
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );
       boolean status =  authentication.isAuthenticated();
       if (status){
          String token =  jwtService.generateToken(loginRequestDto.getUsername());
          return new LoginResponse(token,loginRequestDto.getUsername());
       }
       else{
           throw  new RuntimeException("Invalid username or password");
       }
    }
}

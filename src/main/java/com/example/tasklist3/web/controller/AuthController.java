package com.example.tasklist3.web.controller;

import com.example.tasklist3.domain.user.User;
import com.example.tasklist3.service.AuthService;
import com.example.tasklist3.service.UserService;
import com.example.tasklist3.web.dto.auth.JwtRequest;
import com.example.tasklist3.web.dto.auth.JwtResponse;
import com.example.tasklist3.web.dto.user.UserDto;
import com.example.tasklist3.web.dto.validation.OnCreate;
import com.example.tasklist3.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody final JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody final String refreshToken) {
        return authService.refresh(refreshToken);
    }

}

package com.example.tasklist3.service.impl;

import com.example.tasklist3.service.AuthService;
import com.example.tasklist3.web.dto.auth.JwtRequest;
import com.example.tasklist3.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }

}

package com.example.tasklist3.service;

import com.example.tasklist3.web.dto.auth.JwtRequest;
import com.example.tasklist3.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

}

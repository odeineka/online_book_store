package com.example.demo.service;

import com.example.demo.dto.user.UserLoginRequestDto;
import com.example.demo.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto request);
}

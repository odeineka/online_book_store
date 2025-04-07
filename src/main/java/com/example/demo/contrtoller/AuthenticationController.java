package com.example.demo.contrtoller;

import com.example.demo.dto.user.UserLoginRequestDto;
import com.example.demo.dto.user.UserLoginResponseDto;
import com.example.demo.dto.user.UserRegistrationRequestDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.exception.RegistrationException;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management",
        description = "Endpoints for managing authentication users")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "register users", description = "Get a status about user registration")
    @PostMapping("/registration")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @Operation(summary = "login users", description = "Get token for access")
    @PostMapping("/login")
    public UserLoginResponseDto login(
            @RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}

package com.example.demo.dto.user;

import com.example.demo.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FieldMatch(first = "password", second = "repeatPassword", message = "Passwords must match")
public class UserRegistrationRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters long")
    private String password;

    @NotBlank
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters long")
    private String repeatPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String shippingAddress;
}

package com.example.project1.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailVerifyRequestDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String code;
}

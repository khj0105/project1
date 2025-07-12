package com.example.project1.dto.피드백_auth.request;

import lombok.Getter;

// 공통 로그인 요청 DTO
@Getter
public class LoginRequestDto {
    private String username; // 관리자: schoolCode, 교사/학생: ID
    private String password;
}

package com.example.project1.dto.피드백_auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchoolLoginResponseDto {
    private String schoolCode;
    private Long schoolId;
    private String schoolName;
    private String schoolAdminName;
    private String token;
}

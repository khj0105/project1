package com.example.project1.dto.피드백_auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentLoginResponseDto {
    private String studentId;
    private String studentName;
    private String studentEmail;
    private int studentGrade;
    private String token;
}
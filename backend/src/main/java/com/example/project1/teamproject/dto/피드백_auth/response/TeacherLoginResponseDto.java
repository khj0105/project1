package com.example.project1.teamproject.dto.피드백_auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeacherLoginResponseDto {
    private String teacherId;
    private String teacherName;
    private String teacherEmail;
    private String teacherSubject;
    private String token;
}
package com.example.project1.teamproject.dto.registration.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrolledStudentListDto {
    private String studentId;
    private String studentNumber;
    private String studentName;
    private int studentGrade;
    private int studentClass;
    private LocalDate studentBirthDate;
}
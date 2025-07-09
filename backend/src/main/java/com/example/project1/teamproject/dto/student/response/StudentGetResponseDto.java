package com.example.project1.teamproject.dto.student.response;

import com.example.project1.teamproject.common.enums.StudentStatus;
import com.example.project1.teamproject.common.enums.SubjectAffiliation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StudentGetResponseDto {
    private String studentId;
    private String studentNumber;
    private String studentName;

    @Min(1) @Max(10)
    private int studentClass;

    @Min(1) @Max(3)
    private int studentGrade;

    private String studentEmail;
    private String studentPhoneNumber;
    private LocalDate studentBirthDate;
    private SubjectAffiliation studentAffiliation;
    private int studentAdmissionYear;
    private StudentStatus studentStatus;
}

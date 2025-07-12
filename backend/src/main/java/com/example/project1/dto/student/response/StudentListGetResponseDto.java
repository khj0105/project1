package com.example.project1.dto.student.response;

import com.example.project1.common.enums.SubjectAffiliation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentListGetResponseDto {
    private String studentNumber;
    private String studentName;

    @Min(1) @Max(3)
    private int studentGrade;

    @Min(1) @Max(10)
    private int studentClass;

    private String studentEmail;
    private SubjectAffiliation studentAffiliation;
}

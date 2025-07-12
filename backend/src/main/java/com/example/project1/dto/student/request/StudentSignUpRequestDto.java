package com.example.project1.dto.student.request;

import com.example.project1.common.enums.StudentStatus;
import com.example.project1.common.enums.SubjectAffiliation;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class StudentSignUpRequestDto {

    @NotBlank
    private String studentUsername;

    @NotBlank
    private String studentPassword;

    @NotBlank
    private String studentNumber;

    @NotBlank
    private String studentName;

    @Min(1) @Max(3)
    private int studentGrade;

    @Email
    private String studentEmail;

    @NotBlank
    private String studentPhoneNumber;

    @NotNull
    private String studentBirthDate;

    @NotNull
    private SubjectAffiliation studentAffiliation;

    @Min(2000)
    private int studentAdmissionYear;

    @Pattern(regexp = "male|female", message = "성별은 male 또는 female이어야 합니다.")
    private String studentGender;

    @NotNull
    private StudentStatus studentStatus;

}

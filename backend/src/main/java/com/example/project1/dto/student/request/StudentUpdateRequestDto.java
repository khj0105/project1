package com.example.project1.dto.student.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StudentUpdateRequestDto {

    @NotBlank(message = "이름은 필수입니다.")
    private String studentName;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String studentPhoneNumber;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String studentEmail;
}

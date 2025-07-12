package com.example.project1.dto.subject.request;

import com.example.project1.common.enums.SubjectAffiliation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubjectUpdateRequestDto {

    @NotBlank
    private String subjectName;

    @NotBlank
    private String grade;

    @NotBlank
    private String semester;

    @NotNull
    private SubjectAffiliation affiliation;

    @NotNull
    private Integer maxEnrollment;
}
package com.example.project1.dto.subject.response;

import com.example.project1.common.enums.SubjectAffiliation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectListGetResponseDto {
    private String subjectId;
    private String subjectName;
    private String grade;
    private String semester;
    private SubjectAffiliation affiliation;
}
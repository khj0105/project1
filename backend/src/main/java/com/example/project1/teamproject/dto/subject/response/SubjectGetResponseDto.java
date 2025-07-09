package com.example.project1.teamproject.dto.subject.response;

import com.example.project1.teamproject.common.enums.SubjectAffiliation;
import com.example.project1.teamproject.common.enums.SubjectStatus;
import lombok.*;


@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubjectGetResponseDto {
    private String subjectId;
    private Long schoolId;
    private String subjectName;
    private String grade;
    private String semester;
    private SubjectAffiliation affiliation;
    private SubjectStatus status;
    private Integer maxEnrollment;
}

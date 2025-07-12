package com.example.project1.dto.subject.response;

import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.common.enums.SubjectStatus;
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

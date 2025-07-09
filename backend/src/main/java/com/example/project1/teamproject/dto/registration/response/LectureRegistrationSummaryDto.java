package com.example.project1.teamproject.dto.registration.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureRegistrationSummaryDto {
    private Long lectureId;
    private String subjectName;
    private String teacherName;
    private Integer maxEnrollment;
    private int currentRegisteredCount;
    private List<CourseRegistrationStudentListDto> registeredStudents;
}
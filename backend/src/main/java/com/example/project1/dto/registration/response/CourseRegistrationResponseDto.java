package com.example.project1.dto.registration.response;

import com.example.project1.common.enums.LectureDayOfWeek;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseRegistrationResponseDto {
    private Long registrationId;
    private Long lectureId;
    private String lectureName;
    private String subjectName;
    private String teacherName;
    private LectureDayOfWeek dayOfWeek;
    private int period;
}

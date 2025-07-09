package com.example.project1.teamproject.dto.lecture.response;

import com.example.project1.teamproject.common.enums.LectureDayOfWeek;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureListDto {
    private Long lectureId;
    private String subjectName;
    private String teacherName;
    private LectureDayOfWeek dayOfWeek;
    private int period;
    private int allowedGrade;
}
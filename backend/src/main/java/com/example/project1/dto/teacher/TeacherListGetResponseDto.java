package com.example.project1.dto.teacher;

import com.example.project1.common.enums.TeacherStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherListGetResponseDto {
    private String teacherUsername;
    private String teacherName;
    private String teacherEmail;
    private String teacherPhoneNumber;
    private String teacherSubject;
    private TeacherStatus teacherStatus;
}

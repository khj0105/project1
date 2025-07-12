package com.example.project1.service;

import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.registration.response.EnrolledStudentListDto;
import com.example.project1.dto.registration.response.LectureRegistrationSummaryDto;

import java.util.List;

public interface CourseRegistrationService {
    ResponseDto<LectureRegistrationSummaryDto> getRegisteredStudentsByLectureId(String username, Long lectureId);
    ResponseDto<List<EnrolledStudentListDto>> getEnrolledStudentsByLectureId(String username, Long lectureId);
}

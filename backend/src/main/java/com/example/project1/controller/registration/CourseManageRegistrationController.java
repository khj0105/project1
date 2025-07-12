package com.example.project1.controller.registration;

import com.example.project1.common.ApiMappingPattern;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.registration.response.EnrolledStudentListDto;
import com.example.project1.dto.registration.response.LectureRegistrationSummaryDto;
import com.example.project1.service.CourseRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.API_MANAGE_LECTURE)
@RequiredArgsConstructor
public class CourseManageRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    // 수강신청별 신청 학생 명단 조회 (관리자/교사)
    @GetMapping(ApiMappingPattern.API_MANAGE_LECTURE_REGISTRATIONS_STUDENTS)
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ResponseDto<LectureRegistrationSummaryDto>> getRegisteredStudentsByLectureId(
            @AuthenticationPrincipal String username,
            @PathVariable Long lectureId
    ) {
        ResponseDto<LectureRegistrationSummaryDto> results = courseRegistrationService.getRegisteredStudentsByLectureId(username, lectureId);
        return ResponseEntity.ok(results);
    }

    // 강의별 수강 확정 학생 명단 조회 (관리자/교사)
    @GetMapping(ApiMappingPattern.API_MANAGE_LECTURE_ENROLLED_STUDENTS)
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ResponseDto<List<EnrolledStudentListDto>>> getEnrolledStudentsByLectureId(
            @AuthenticationPrincipal String username,
            @PathVariable Long lectureId
    ) {
        ResponseDto<List<EnrolledStudentListDto>> results = courseRegistrationService.getEnrolledStudentsByLectureId(username, lectureId);
        return ResponseEntity.ok(results);
    }
}
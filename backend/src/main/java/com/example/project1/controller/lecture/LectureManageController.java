package com.example.project1.controller.lecture;

import com.example.project1.common.ApiMappingPattern;
import com.example.project1.common.enums.LectureDayOfWeek;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.lecture.request.LectureUpdateRequestDto;
import com.example.project1.dto.lecture.response.LectureDetailDto;
import com.example.project1.dto.lecture.response.LectureListDto;
import com.example.project1.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.API_MANAGE_LECTURE)
@RequiredArgsConstructor
public class LectureManageController {

    private final LectureService lectureService;

    // 전체 강의 목록 조회
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ResponseDto<List<LectureListDto>>> searchLectures(
            @AuthenticationPrincipal String username,
            @RequestParam(required = false) Long lectureId,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) LectureDayOfWeek dayOfWeek,
            @RequestParam(defaultValue = "0") int period,
            @RequestParam(defaultValue = "0") int allowedGrade
    ) {
        ResponseDto<List<LectureListDto>> results = lectureService.searchLectures(username, lectureId, subjectName, teacherName, dayOfWeek, period, allowedGrade);
        return ResponseEntity.ok(results);
    }

    // 강의 상세 조회
    @GetMapping("/{lectureId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ResponseDto<LectureDetailDto>> findByStudentId(
            @AuthenticationPrincipal String username,
            @PathVariable Long lectureId
    ) {
        ResponseDto<LectureDetailDto> result = lectureService.findByStudentId(username, lectureId);
        return ResponseEntity.ok(result);
    }

    // 강의 수정
    @PutMapping("/{lectureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<LectureListDto>> updateLecture(
            @AuthenticationPrincipal String username,
            @PathVariable Long lectureId,
            @Valid @RequestBody LectureUpdateRequestDto requestDto) {

        ResponseDto<LectureListDto> response = lectureService.updateLecture(username, lectureId, requestDto); // username 전달
        return ResponseEntity.ok(response);
    }

    // 강의 삭제
    @DeleteMapping("/{lectureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<?>> deleteLecture(
            @AuthenticationPrincipal String username,
            @PathVariable Long lectureId) {

        ResponseDto<?> response = lectureService.deleteLecture(username, lectureId); // username 전달
        return ResponseEntity.ok(response);
    }
}

package com.example.project1.controller.registration;

import com.example.project1.dto.registration.request.CourseRegistrationRequestDto;
import com.example.project1.dto.registration.response.CourseRegistrationResponseDto;
import com.example.project1.provider.JwtProvider;
import com.example.project1.service.impl.LectureService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-registrations")
@RequiredArgsConstructor
public class CourseRegistrationController {

    private final LectureService.CourseRegistrationService courseRegistrationService;
    private final JwtProvider jwtTokenProvider;

    // 수강신청 등록
    @PostMapping
    public ResponseEntity<String> registerCourse(@RequestBody CourseRegistrationRequestDto requestDto,
                                                 HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        courseRegistrationService.registerByEmail(email, requestDto);
        return ResponseEntity.ok("수강신청이 완료되었습니다.");
    }

    // 내 수강신청 목록 조회
    @GetMapping("/my")
    public ResponseEntity<List<CourseRegistrationResponseDto>> getMyRegistrations(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        List<CourseRegistrationResponseDto> registrations = courseRegistrationService.getMyRegistrations(email);
        return ResponseEntity.ok(registrations);
    }

    // 수강신청 목록 상세 조회
    @GetMapping("/{registrationId}")
    public ResponseEntity<CourseRegistrationResponseDto> getRegistrationDetail(
            @PathVariable Long registrationId,
            HttpServletRequest request) {

        String email = jwtTokenProvider.getEmailFromRequest(request);
        CourseRegistrationResponseDto detail = courseRegistrationService.getRegistrationDetail(email, registrationId);
        return ResponseEntity.ok(detail);
    }

    // 수강신청 취소
    @PostMapping("/{registrationId}/cancellation")
    public ResponseEntity<String> cancelRegistration(@PathVariable Long registrationId,
                                                     HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        courseRegistrationService.cancelRegistration(email, registrationId);
        return ResponseEntity.ok("수강신청이 취소되었습니다.");
    }

}

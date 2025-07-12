package com.example.project1.controller.student;

import com.example.project1.common.ApiMappingPattern;
import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.student.response.StudentGetResponseDto;
import com.example.project1.dto.student.response.StudentListGetResponseDto;
import com.example.project1.service.StudentManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.API_MANAGE_STUDENT)
@RequiredArgsConstructor
public class StudentManageController {

    private final StudentManageService studentService;

    private static final String GET_SEARCH_STUDENT_BY_ID = "/{studentId}";

    // 1) 학생 정보 조회 (단건 반환)
    @GetMapping(GET_SEARCH_STUDENT_BY_ID)
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ResponseDto<StudentGetResponseDto>> findByStudentId(
            @AuthenticationPrincipal String username,
            @PathVariable String studentId) {
        ResponseDto<StudentGetResponseDto> response = studentService.findByStudentId(username, studentId);
        return ResponseEntity.ok(response);
    }

    // 학생 목록 조회 (다건 반환)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ResponseDto<List<StudentListGetResponseDto>>> searchStudents(
            @AuthenticationPrincipal String username,
            @RequestParam(required = false)String studentNumber,
            @RequestParam(required = false)String studentName,
            @RequestParam(defaultValue = "0")int studentGrade,
            @RequestParam(defaultValue = "0")int studentClass,
            @RequestParam(required = false)SubjectAffiliation studentAffiliation
    ) {
        ResponseDto<List<StudentListGetResponseDto>> results = studentService.searchStudents(username, studentNumber, studentName, studentGrade, studentClass, studentAffiliation);
        return ResponseEntity.ok(results);
    }

}

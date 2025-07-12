package com.example.project1.controller.common;

import com.example.project1.common.ApiMappingPattern;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.common.SchoolListDto;
import com.example.project1.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.API_COMMON)
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    // 모든 학교 목록 조회
    @GetMapping("/schools")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ResponseDto<List<SchoolListDto>>> getAllSchools() {
        ResponseDto<List<SchoolListDto>> response = schoolService.getAllSchools();
        return ResponseEntity.ok(response);
    }
}
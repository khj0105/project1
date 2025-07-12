package com.example.project1.service;

import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.student.response.StudentGetResponseDto;
import com.example.project1.dto.student.response.StudentListGetResponseDto;

import java.util.List;

public interface StudentManageService {
    // 학생 상세 조회 (반환 단건) - 교사 / 관리자
    ResponseDto<StudentGetResponseDto> findByStudentId(String username, String studentId);

    // 학생 목록 검색 조회 (반환 LIST) - 교사 / 관리자
    ResponseDto<List<StudentListGetResponseDto>> searchStudents(String username, String studentNumber, String studentName, int studentGrade, int studentClass, SubjectAffiliation   studentAffiliation);
}

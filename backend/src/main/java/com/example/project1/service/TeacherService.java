package com.example.project1.service;

import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.teacher.TeacherListGetResponseDto;

import java.util.List;

public interface TeacherService {
    // 선생 전체 조회 (반환 LIST) - 교사 / 관리자
    ResponseDto<List<TeacherListGetResponseDto>> getTeacherList(String username);
}

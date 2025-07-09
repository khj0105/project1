package com.example.project1.teamproject.service;

import com.example.project1.teamproject.dto.common.ResponseDto;
import com.example.project1.teamproject.dto.teacher.TeacherListGetResponseDto;

import java.util.List;

public interface TeacherService {
    // 선생 전체 조회 (반환 LIST) - 교사 / 관리자
    ResponseDto<List<TeacherListGetResponseDto>> getTeacherList(String username);
}

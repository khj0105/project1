package com.example.project1.teamproject.service;

import com.example.project1.teamproject.dto.common.ResponseDto;
import com.example.project1.teamproject.dto.common.SchoolListDto;

import java.util.List;

public interface SchoolService {
    // 모든 학교 목록 조회 (회원/교직원 가입 시 활용)
    ResponseDto<List<SchoolListDto>> getAllSchools();
}
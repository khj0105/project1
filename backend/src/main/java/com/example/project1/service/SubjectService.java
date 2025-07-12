package com.example.project1.service;

import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.common.enums.SubjectStatus;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.subject.response.SubjectGetResponseDto;
import com.example.project1.dto.subject.response.SubjectListGetResponseDto;

import java.util.List;

public interface SubjectService {

    // 과목 목록 검색 조회 (반환 LIST) - 교사 / 관리자
    ResponseDto<List<SubjectListGetResponseDto>> searchSubjects(String userId, String subjectName, String grade, String semester, SubjectAffiliation affiliation);

    // 과목 상세 조회 (반환 단건) - 교사 / 관리자
    ResponseDto<SubjectGetResponseDto> getSubjectById(String userId, String subjectId);

    // 과목 상태 변경 (승인/거절/삭제) - 관리자
    ResponseDto<SubjectGetResponseDto> updateSubjectStatus(String username, String subjectId, SubjectStatus newStatus);

    // 과목 수정 - 교사 (프론트엔드 파트 담당)
}
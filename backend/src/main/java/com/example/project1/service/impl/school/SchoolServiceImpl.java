package com.example.project1.service.impl.school;

import com.example.project1.common.ResponseMessage;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.common.SchoolListDto;
import com.example.project1.entity.School;
import com.example.project1.repository.SchoolRepository;
import com.example.project1.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 트랜잭션
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    public ResponseDto<List<SchoolListDto>> getAllSchools() {
        List<School> schools = schoolRepository.findAll();

        List<SchoolListDto> responseData = schools.stream()
                .map(school -> SchoolListDto.builder()
                        .schoolId(school.getId())
                        .schoolCode(school.getSchoolCode())
                        .schoolName(school.getSchoolName())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.GET_SCHOOL_LIST_SUCCESS, responseData);
    }
}
package com.example.project1.teamproject.service.impl.auth;

import com.example.project1.teamproject.dto.auth.request.LoginRequestDto;
import com.example.project1.teamproject.dto.common.ResponseDto;
import com.example.project1.teamproject.dto.피드백_auth.response.SchoolLoginResponseDto;
import com.example.project1.teamproject.entity.School;
import com.example.project1.teamproject.provider.JwtProvider;
import com.example.project1.teamproject.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolAuthServiceImpl {
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtTokenProvider;

    public ResponseEntity<ResponseDto<SchoolLoginResponseDto>> login(LoginRequestDto dto) {
        School school = schoolRepository.findBySchoolCode(dto.getUsername())
                .orElseThrow();

        if (!passwordEncoder.matches(dto.getPassword(), school.getSchoolPassword())) {
            //
        }

        String token = jwtTokenProvider.generateJwtToken(school.getSchoolCode(), "SCHOOL");

        SchoolLoginResponseDto responseDto = new SchoolLoginResponseDto(
                school.getSchoolCode(),
                school.getId(),
                school.getSchoolName(),
                school.getSchoolAdminName(),
                token
        );

        return ResponseEntity.ok(ResponseDto.setSuccess("학교 관리자 로그인 성공", responseDto));
    }
}
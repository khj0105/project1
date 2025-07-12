package com.example.project1.service.impl;

import com.example.project1.common.enums.ErrorCode;
import com.example.project1.common.exception.CustomException;
import com.example.project1.dto.auth.request.LoginRequestDto;
import com.example.project1.entity.Student;
import com.example.project1.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentAuthService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequestDto request) {

        // 1. 사용자 존재 여부 확인
        Student student = studentRepository.findByStudentUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), student.getStudentPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 3. 로그인 성공 처리 (현재는 문자열로 반환)
        return "로그인 성공: " + student.getStudentId();
    }
}

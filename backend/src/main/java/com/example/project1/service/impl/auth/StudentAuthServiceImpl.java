package com.example.project1.service.impl.auth;

import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.피드백_auth.request.LoginRequestDto;
import com.example.project1.dto.피드백_auth.response.StudentLoginResponseDto;
import com.example.project1.entity.Student;
import com.example.project1.provider.JwtProvider;
import com.example.project1.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentAuthServiceImpl {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtTokenProvider;

    public ResponseEntity<ResponseDto<StudentLoginResponseDto>> login(LoginRequestDto dto) {
        Student student = studentRepository.findByStudentUsername(dto.getUsername())
                .orElseThrow();

        if (!passwordEncoder.matches(dto.getPassword(), student.getStudentPassword())) {
            //
        }

        String token = jwtTokenProvider.generateJwtToken(student.getStudentId(), "STUDENT");

        StudentLoginResponseDto responseDto = new StudentLoginResponseDto(
                student.getStudentId(),
                student.getStudentName(),
                student.getStudentEmail(),
                student.getStudentGrade(),
                token
        );

        return ResponseEntity.ok(ResponseDto.setSuccess("학생 로그인 성공", responseDto));
    }
}
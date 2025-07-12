package com.example.project1.service.impl.auth;

import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.피드백_auth.request.LoginRequestDto;
import com.example.project1.dto.피드백_auth.response.TeacherLoginResponseDto;
import com.example.project1.entity.Teacher;
import com.example.project1.provider.JwtProvider;
import com.example.project1.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherAuthServiceImpl {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtTokenProvider;

    public ResponseEntity<ResponseDto<TeacherLoginResponseDto>> login(LoginRequestDto dto) {
        boolean is_teacher = teacherRepository.existsByTeacherUsername(dto.getUsername());

        if (!is_teacher) {
            return ResponseEntity.badRequest().body(ResponseDto.setFailed("아이디가 올바르지 않습니다."));
        }

        Teacher teacher = teacherRepository.getTeacherList(dto.getUsername()).get();

        if (!passwordEncoder.matches(dto.getPassword(), teacher.getTeacherPassword())) {
            return ResponseEntity.badRequest().body(ResponseDto.setFailed("비밀번호가 올바르지 않습니다."));
        }

        String token = jwtTokenProvider.generateJwtToken(teacher.getTeacherId(), "TEACHER");

        TeacherLoginResponseDto responseDto = new TeacherLoginResponseDto(
                teacher.getTeacherId(),
                teacher.getTeacherName(),
                teacher.getTeacherEmail(),
                teacher.getTeacherSubject(),
                token
        );

        return ResponseEntity.ok(ResponseDto.setSuccess("교사 로그인 성공", responseDto));
    }
}
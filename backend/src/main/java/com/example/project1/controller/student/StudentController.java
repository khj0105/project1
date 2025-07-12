package com.example.project1.controller.student;

import com.example.project1.dto.auth.request.LoginRequestDto;
import com.example.project1.dto.student.request.StudentSignUpRequestDto;
import com.example.project1.dto.student.request.StudentUpdateRequestDto;
import com.example.project1.dto.student.response.StudentInfoResponseDto;
import com.example.project1.entity.Student;
import com.example.project1.service.impl.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody StudentSignUpRequestDto request) {
        studentService.signUp(request);
        return ResponseEntity.ok("학생 회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto request) {
        Student student = studentService.login(request);
        return ResponseEntity.ok("로그인 성공: " + student.getStudentName());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentInfoResponseDto> getStudentInfo(@PathVariable String studentId) {
        StudentInfoResponseDto info = studentService.getStudentInfo(studentId);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<String> updateStudentInfo(
            @PathVariable String studentId,
            @Valid @RequestBody StudentUpdateRequestDto request) {
        studentService.updateStudentInfo(studentId, request);
        return ResponseEntity.ok("학생 정보가 수정되었습니다.");
    }
}

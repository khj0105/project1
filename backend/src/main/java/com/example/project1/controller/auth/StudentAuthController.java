package com.example.project1.controller.auth;

import com.example.project1.dto.auth.request.LoginRequestDto;
import com.example.project1.service.impl.StudentAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class StudentAuthController {

    private final StudentAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);  // JWT를 사용하는 경우
    }

//    @PostMapping("/login")
//    public ResponseEntity<ResponseDto<StudentLoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {
//        return studentAuthService.login(requestDto);
//    }
}

package com.example.project1.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("error", e.getErrorCode().name());
        errorBody.put("message", e.getErrorCode().getMessage());
        errorBody.put("status", 400); // 필요 시 ErrorCode에 상태코드도 포함 가능

        return ResponseEntity
                .badRequest()
                .body(errorBody);
    }
}

package com.example.project1.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private String message;
    private T data;

    // 성공 응답
    public static <T> ResponseDto<T> setSuccess(String message, T data) {
        return new ResponseDto<>(true, message, data);
    }

    // 실패 응답
    public static <T> ResponseDto<T> setFailed(String message) {
        return new ResponseDto<>(false, message, null);
    }

    // (선택) 성공 메시지 기본값
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, "요청 성공", data);
    }

    // (선택) 실패 시 데이터 포함
    public static <T> ResponseDto<T> fail(String message, T data) {
        return new ResponseDto<>(false, message, data);
    }
}


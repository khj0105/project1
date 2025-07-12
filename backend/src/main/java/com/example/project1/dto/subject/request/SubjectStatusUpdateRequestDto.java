package com.example.project1.dto.subject.request;

import com.example.project1.common.enums.SubjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; // @RequestBody 사용 시 필요

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectStatusUpdateRequestDto {
    @NotNull(message = "과목 상태는 필수입니다.") // 유효성 검사 메시지 추가
    private SubjectStatus status;
}
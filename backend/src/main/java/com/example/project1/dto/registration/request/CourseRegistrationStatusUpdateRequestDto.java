package com.example.project1.dto.registration.request;

import com.example.project1.common.enums.CourseRegistrationStatus; // 통일된 Enum 사용
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistrationStatusUpdateRequestDto {
    @NotNull(message = "수강신청 상태는 필수입니다.")
    private CourseRegistrationStatus status;
}
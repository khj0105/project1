package com.example.project1.dto.lecture.request;

import com.example.project1.common.enums.LectureDayOfWeek;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureUpdateRequestDto {
    @NotBlank
    private String teacherId;

    @NotNull
    private LectureDayOfWeek dayOfWeek;

    @NotNull
    @Min(1) @Max(8)
    private int period;

    @NotNull
    @Min(0) @Max(30)
    private int maxEnrollment;
}
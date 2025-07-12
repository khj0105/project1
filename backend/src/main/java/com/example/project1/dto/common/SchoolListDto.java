package com.example.project1.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolListDto {
    private Long schoolId;
    private String schoolCode;
    private String schoolName;
}
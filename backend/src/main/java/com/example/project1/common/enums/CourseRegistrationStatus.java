package com.example.project1.common.enums;

import lombok.Getter;

@Getter
public enum CourseRegistrationStatus {
    WAITING("수강 대기"),
    CONFIRMED("수강 확정"),
    REJECTED("수강 탈락"),
    CANCELED("수강 취소");

    private final String description;

    CourseRegistrationStatus(String description) {
        this.description = description;
    }
}

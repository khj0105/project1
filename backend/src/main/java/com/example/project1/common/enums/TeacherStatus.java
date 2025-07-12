package com.example.project1.common.enums;

import lombok.Getter;

@Getter
public enum TeacherStatus {
    PENDING("승인 대기"),
    APPROVED("승인 완료"),
    ON_LEAVE("휴직"),
    RETIRED("퇴직");

    private final String description;
    TeacherStatus(String description) {
        this.description = description;
    }
}
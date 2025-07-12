package com.example.project1.common.enums;

import lombok.Getter;

@Getter
public enum StudentStatus {
    PENDING("승인 대기"),
    APPROVED("승인 완료"),
    REJECTED("승인 반려"),
    GRADUATED("졸업");

    private final String description;
    StudentStatus(String description) {
        this.description = description;
    }
}
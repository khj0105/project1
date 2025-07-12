package com.example.project1.common.enums;

import lombok.Getter;

@Getter
public enum courseRegistrationApprovalStatus {
    PENDING("대기"),
    CONFIRMED("확정"),
    CANCELED("취소");

    private final String description;

    courseRegistrationApprovalStatus(String description) {
        this.description = description;
    }
}

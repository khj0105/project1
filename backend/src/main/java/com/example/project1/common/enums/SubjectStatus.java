package com.example.project1.common.enums;

import lombok.Getter;

@Getter
public enum SubjectStatus {
    APPROVAL("승인 완료"),
    REJECTED("승인 거절"),
    DELETED("과목 삭제");

    private final String description;
    SubjectStatus(String description) {
        this.description = description;
    }
}
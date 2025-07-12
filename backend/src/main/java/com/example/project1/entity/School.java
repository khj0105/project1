package com.example.project1.entity;

import com.example.project1.common.enums.SchoolStatus;
import com.example.project1.entity.datatime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "school")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class School extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Column(name = "school_code", nullable = false, unique = true)
    private String schoolCode;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "school_address", nullable = false)
    private String schoolAddress;

    @Column(name = "school_contact_number", nullable = false)
    private String schoolContactNumber;

    @Column(name = "school_password", nullable = false)
    private String schoolPassword;

    @Column(name = "school_admin_name", nullable = false)
    private String schoolAdminName;

    @Column(name = "school_admin_phone_number", nullable = false)
    private String schoolAdminPhoneNumber;

    @Column(name = "school_admin_email", nullable = false)
    private String schoolAdminEmail;

    @Column(name = "application_started_day", nullable = false)
    private LocalDate applicationStartedDay;

    @Column(name = "application_limited_day", nullable = false)
    private LocalDate applicationLimitedDay;

    @Builder.Default
    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "school_status", nullable = false)
    private SchoolStatus status;

    @Builder.Default
    @Column(name = "school_code_verification_key", length = 50)
    private String schoolCodeVerificationKey = "";

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void updateSchoolInfo(String address, String contactNumber, String adminName, String adminPhoneNumber, String adminEmail) {
        this.schoolAddress = address;
        this.schoolContactNumber = contactNumber;
        this.schoolAdminName = adminName;
        this.schoolAdminPhoneNumber = adminPhoneNumber;
        this.schoolAdminEmail = adminEmail;
    }

    // 학교 관리자 비밀번호를 변경
    public void changePassword(String newPassword) {
        this.schoolPassword = newPassword;
    }

    // 수강 신청 기간을 수정
    public void updateApplicationPeriod(LocalDate startDate, LocalDate limitedDate) {
        this.applicationStartedDay = startDate;
        this.applicationLimitedDay = limitedDate;
    }

    public void verifyEmail() {
        this.isEmailVerified = true;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void updateSchoolMainInfo(String name, String address, String contactNumber) {
        this.schoolName = name;
        this.schoolAddress = address;
        this.schoolContactNumber = contactNumber;
    }

    public void updateStatus(SchoolStatus newStatus) {
        this.status = newStatus;
    }
}
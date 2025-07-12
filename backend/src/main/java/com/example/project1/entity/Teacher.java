package com.example.project1.entity;

import com.example.project1.common.enums.TeacherStatus;
import com.example.project1.entity.datatime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Teacher extends BaseTimeEntity {

    @Id
    @Column(name = "teacher_id")
    private String teacherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School schoolId;

    @Column(name = "teacher_username", unique = true, nullable = false)
    private String teacherUsername;

    @Column(name = "teacher_password", nullable = false)
    private String teacherPassword;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "teacher_email", unique = true, nullable = false)
    private String teacherEmail;

    @Column(name = "teacher_phone_number", nullable = false)
    private String teacherPhoneNumber;

    @Column(name = "teacher_subject", nullable = false)
    private String teacherSubject;

    @Enumerated(EnumType.STRING)
    @Column(name = "teacher_status", nullable = false)
    private TeacherStatus teacherStatus;


//    // 교사의 개인 정보(이메일, 전화번호, 담당 과목)를 수정
//    public void updateInfo(String email, String phoneNumber, String subject) {
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.subject = subject;
//    }
//
//    // 비밀번호를 변경
//    public void changePassword(String newPassword) {
//        this.password = newPassword;
//    }
//
//    // 가입 신청을 승인하여 '재직' 상태로 변경
//    public void approve() {
//        if (this.status != TeacherStatus.PENDING) {
//            throw new IllegalStateException("승인 대기 상태의 교사만 승인할 수 있습니다.");
//        }
//        this.status = TeacherStatus.APPROVED;
//    }
//
//    // '재직' 상태의 교사를 '휴직' 상태로 변경
//    public void takeLeave() {
//        if (this.status != TeacherStatus.APPROVED) {
//            throw new IllegalStateException("재직 중인 교사만 휴직 처리할 수 있습니다.");
//        }
//        this.status = TeacherStatus.ON_LEAVE;
//    }
//
//    // '휴직' 상태의 교사를 '재직' 상태로 복직
//    public void reinstate() {
//        if (this.status != TeacherStatus.ON_LEAVE) {
//            throw new IllegalStateException("휴직 중인 교사만 복직 처리할 수 있습니다.");
//        }
//        this.status = TeacherStatus.APPROVED;
//    }
//
//    // 교사를 '퇴직' 상태로 변경
//    public void retire() {
//        this.status = TeacherStatus.RETIRED;
//    }
}
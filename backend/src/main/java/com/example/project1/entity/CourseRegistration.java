package com.example.project1.entity;

import com.example.project1.common.enums.CourseRegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_registration")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    // ✅ 수강신청 상태 추가
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseRegistrationStatus status;
}

package com.example.project1.entity;

import com.example.project1.common.enums.LectureDayOfWeek;
import com.example.project1.dto.lecture.request.LectureUpdateRequestDto;
import com.example.project1.entity.datatime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lecture")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Lecture extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long lectureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "lecture_day_of_week", nullable = false)
    private LectureDayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    @Column(name = "period", nullable = false)
    private int period; // 교시 (예: 1교시, 2교시)

    @Column(name = "lecture_allowed_grade", nullable = false)
    private int allowedGrade;

    @Column(name = "lecture_max_enrollment", nullable = false)
    private Integer maxEnrollment;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseRegistration> courseRegistrations;

    public void updateInfo(Teacher teacher, LectureUpdateRequestDto dto) {
        this.teacher = teacher;
        this.dayOfWeek = dto.getDayOfWeek();
        this.period = dto.getPeriod();
        this.maxEnrollment = dto.getMaxEnrollment();
    }
}

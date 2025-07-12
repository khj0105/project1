package com.example.project1.entity;

import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.common.enums.SubjectStatus;
import com.example.project1.dto.subject.request.SubjectUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Subject {
    @Id
    @Column(name = "subject_id")
    private String subjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "subject_grade", nullable = false)
    private String grade;

    @Column(name = "subject_semester", nullable = false)
    private String semester;

    @Column(name = "subject_description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_affiliation", nullable = false)
    private SubjectAffiliation affiliation;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_status", nullable = false)
    private SubjectStatus status;

    @Column(name = "subject_max_enrollment", nullable = false)
    private Integer maxEnrollment;

    public void updateStatus(SubjectStatus newStatus) {
        this.status = newStatus;
    }

    public void updateInfo(SubjectUpdateRequestDto dto) {
        this.subjectName = dto.getSubjectName();
        this.grade = dto.getGrade();
        this.semester = dto.getSemester();
        this.affiliation = dto.getAffiliation();
        this.maxEnrollment = dto.getMaxEnrollment();
    }


}

package com.example.project1.entity;

import com.example.project1.common.enums.StudentStatus;
import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.entity.datatime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Student extends BaseTimeEntity {

    @Id
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "student_username", unique = true, nullable = false)
    private String studentUsername;

    @Column(name = "student_password", nullable = false)
    private String studentPassword;

    @Column(name = "student_number", unique = true, nullable = false)
    private String studentNumber;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_class", nullable = false)
    private int studentClass;

    @Column(name = "student_grade", nullable = false)
    private int studentGrade;

    @Column(name = "student_email", nullable = false)
    private String studentEmail;

    @Column(name = "student_phone_number", nullable = false)
    private String studentPhoneNumber;

    @Column(name = "student_birth_date", nullable = false)
    private LocalDate studentBirthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_affiliation", nullable = false)
    private SubjectAffiliation studentAffiliation;

    @Column(name = "student_admission_year", nullable = false)
    private int studentAdmissionYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_status", nullable = false)
    private StudentStatus studentStatus;

    public void update(String studentName, String studentPhoneNumber, String studentEmail) {
        this.studentName = studentName;
        this.studentPhoneNumber = studentPhoneNumber;
        this.studentEmail = studentEmail;
    }

    @OneToMany(mappedBy = "student")
    private List<CourseRegistration> courseRegistrations;

}

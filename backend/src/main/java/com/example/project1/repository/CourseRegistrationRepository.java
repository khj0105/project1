package com.example.project1.repository;

import com.example.project1.common.enums.CourseRegistrationStatus;
import com.example.project1.entity.CourseRegistration;
import com.example.project1.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

    // 시간표 조회용
    List<CourseRegistration> findByStudentAndStatus(Student student, CourseRegistrationStatus status);

    // 중복 수강신청 확인
    boolean existsByStudent_StudentIdAndLecture_LectureId(String studentId, Long lectureId);

    // 수강인원 확인 (전체 카운트)
    int countByLecture_LectureId(Long lectureId);

    // 특정 학생의 전체 수강신청 목록 조회
    List<CourseRegistration> findByStudent_StudentId(String studentId);

    // 수강신청 상세 조회 (학생 본인 여부 확인 포함)
    Optional<CourseRegistration> findByIdAndStudent_StudentId(Long registrationId, String studentId);

    // 특정 강의에 대한 확정 수강 인원만 카운트
    int countByLecture_LectureIdAndStatus(Long lectureId, CourseRegistrationStatus status);

    // 특정 학생 + 강의 조합 직접 조회
    Optional<CourseRegistration> findByStudent_StudentIdAndLecture_LectureId(String studentId, Long lectureId);

   // -------------------------------------------------------------------------------------------------------------------
    @Query("SELECT cr FROM CourseRegistration cr JOIN FETCH cr.student s WHERE cr.lecture.lectureId = :lectureId")
    List<CourseRegistration> findByLecture_LectureIdWithStudent(@Param("lectureId") Long lectureId);

    @Query("SELECT cr FROM CourseRegistration cr JOIN FETCH cr.student s WHERE cr.lecture.lectureId = :lectureId AND cr.status = 'CONFIRMED'")
    List<CourseRegistration> findByLecture_LectureIdAndStatusConfirmedWithStudent(@Param("lectureId") Long lectureId);

    int countByLecture_LectureIdAndStatusIsNot(Long lectureId, CourseRegistrationStatus status);
}

package com.example.project1.repository;

import com.example.project1.common.enums.LectureDayOfWeek;
import com.example.project1.entity.Lecture;
import com.example.project1.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT l FROM Lecture l JOIN FETCH l.subject s JOIN FETCH l.teacher t")
    List<Lecture> findAllWithSubjectAndTeacher();

    @Query("SELECT l FROM Lecture l JOIN FETCH l.subject s JOIN FETCH l.teacher t JOIN FETCH l.classroom c WHERE l.lectureId = :lectureId")
    Optional<Lecture> findByIdWithSubjectTeacherClassroom(Long lectureId);

    // manage 권한 (ADMIN, TEACHER)
    List<Lecture> findBySubject_SubjectNameContaining(String subjectName);

    boolean existsBySubject(Subject subject);

    @Query("SELECT l FROM Lecture l JOIN FETCH l.subject s JOIN FETCH l.teacher t WHERE " +
            "(:lectureId IS NULL OR l.lectureId = :lectureId) AND " +
            "(:subjectName IS NULL OR :subjectName = '' OR s.subjectName LIKE %:subjectName%) AND " +
            "(:teacherName IS NULL OR :teacherName = '' OR t.teacherName LIKE %:teacherName%) AND " +
            "(:dayOfWeek IS NULL OR l.dayOfWeek = :dayOfWeek) AND " +
            "(:period = 0 OR l.period = :period) AND " +
            "(:allowedGrade = 0 OR l.allowedGrade = :allowedGrade)")
    List<Lecture> searchLectures(
            @Param("lectureId") Long lectureId,
            @Param("subjectName") String subjectName,
            @Param("teacherName") String teacherName,
            @Param("dayOfWeek") LectureDayOfWeek dayOfWeek,
            @Param("period") int period,
            @Param("allowedGrade") int allowedGrade
    );

    // admin 권한
}

package com.example.project1.repository;

import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    // manage 권한 (ADMIN, TEACHER)
    @Query("SELECT s FROM Subject s WHERE " +
            "(:subjectName IS NULL OR :subjectName = '' OR s.subjectName LIKE %:subjectName%) AND " +
            "(:grade IS NULL OR :grade = '' OR s.grade = :grade) AND " +
            "(:semester IS NULL OR :semester = '' OR s.semester = :semester) AND " +
            "(:affiliation IS NULL OR s.affiliation = :affiliation)")
    List<Subject> searchSubjects(
            @Param("subjectName") String subjectName,
            @Param("grade") String grade,
            @Param("semester") String semester,
            @Param("affiliation") SubjectAffiliation affiliation
    );

}
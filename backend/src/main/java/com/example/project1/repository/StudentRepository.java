package com.example.project1.repository;

import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // manage 권한 (ADMIN, TEACHER)
    Optional<Student> findByStudentId(String studentId);
    //List<Student> searchStudents(String studentNumber, String studentName, int studentGrade, int studentClass, SubjectAffiliation studentAffiliation);
    @Query("SELECT s FROM Student s WHERE " +
            "(:studentNumber IS NULL OR :studentNumber = '' OR s.studentNumber LIKE %:studentNumber%) AND " +
            "(:studentName IS NULL OR :studentName = '' OR s.studentName LIKE %:studentName%) AND " +
            "(:studentGrade = 0 OR s.studentGrade = :studentGrade) AND " +
            "(:studentClass = 0 OR s.studentClass = :studentClass) AND " +
            "(:studentAffiliation IS NULL OR s.studentAffiliation = :studentAffiliation)")
    List<Student> searchStudents(
            @Param("studentNumber") String studentNumber,
            @Param("studentName") String studentName,
            @Param("studentGrade") int studentGrade,
            @Param("studentClass") int studentClass,
            @Param("studentAffiliation") SubjectAffiliation studentAffiliation
    );

    // student 권한 (STUDENT)
    boolean existsByStudentUsername(String studentUsername);
    boolean existsByStudentEmail(String studentEmail);
    Optional<Student> findByStudentUsername(String studentUsername);
    Optional<Student> findByStudentEmail(String studentEmail);
}

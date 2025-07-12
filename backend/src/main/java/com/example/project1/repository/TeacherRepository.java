package com.example.project1.repository;

import com.example.project1.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    boolean existsByTeacherUsername(String username);

    Optional<Teacher> getTeacherList(String username);
}
package com.example.project1.teamproject.repository;

import com.example.project1.teamproject.common.enums.SchoolStatus;
import com.example.project1.teamproject.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    boolean existsBySchoolCode(String schoolCode);

    boolean existsBySchoolAdminEmail(String email);

    Optional<School> findBySchoolAdminEmail(String email);

    Optional<School> findBySchoolCodeAndDeletedAtIsNull(String schoolCode);

    Optional<School> findBySchoolAdminEmailAndIsEmailVerifiedTrue(String email);

    Optional<School> findBySchoolCode(String schoolCode);

    Optional<School> findBySchoolAdminNameAndIsEmailVerifiedTrue(String schoolAdminName);

    // 상태로 필터링하는 메서드 추가
    List<School> findAllByStatus(SchoolStatus status);
}
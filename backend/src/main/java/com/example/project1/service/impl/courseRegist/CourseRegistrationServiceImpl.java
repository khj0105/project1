package com.example.project1.service.impl.courseRegist;

import com.example.project1.common.ResponseMessage;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.registration.response.CourseRegistrationStudentListDto;
import com.example.project1.dto.registration.response.EnrolledStudentListDto;
import com.example.project1.dto.registration.response.LectureRegistrationSummaryDto;
import com.example.project1.entity.CourseRegistration;
import com.example.project1.entity.Lecture; // Lecture 엔티티 import 추가
import com.example.project1.entity.Student; // Student 엔티티 import 추가
import com.example.project1.repository.CourseRegistrationRepository;
import com.example.project1.repository.LectureRepository; // LectureRepository 주입받음
import com.example.project1.repository.SchoolRepository;
import com.example.project1.repository.TeacherRepository;
import com.example.project1.service.CourseRegistrationService; // 올바른 인터페이스 임포트
import jakarta.persistence.EntityNotFoundException; // EntityNotFoundException import 추가
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션
public class CourseRegistrationServiceImpl implements CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final SchoolRepository schoolRepository; // isAuthorized 로직 유지를 위해 주입
    private final TeacherRepository teacherRepository; // isAuthorized 로직 유지를 위해 주입
    private final LectureRepository lectureRepository; // 강의 존재 여부 확인 및 정보 조회를 위해 주입

    // 강의별 수강신청 학생 명단 조회 (관리자/교사)
    @Override
    public ResponseDto<LectureRegistrationSummaryDto> getRegisteredStudentsByLectureId(String username, Long lectureId) {
        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        Lecture lecture = lectureRepository.findById(lectureId) // existsById 대신 findById 사용
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LECTURE + ": " + lectureId));

        int totalRegisteredCount = courseRegistrationRepository.countByLecture_LectureId(lectureId);

        List<CourseRegistration> registrations = courseRegistrationRepository.findByLecture_LectureIdWithStudent(lectureId);

        List<CourseRegistrationStudentListDto> registeredStudentsDto = registrations.stream()
                .map(registration -> {
                    Student student = registration.getStudent();
                    return CourseRegistrationStudentListDto.builder()
                            .studentId(student.getStudentId())
                            .studentNumber(student.getStudentNumber())
                            .studentName(student.getStudentName())
                            .studentGrade(student.getStudentGrade())
                            .studentClass(student.getStudentClass())
                            .studentEmail(student.getStudentEmail())
                            .status(registration.getStatus())
                            .build();
                })
                .collect(Collectors.toList());

        LectureRegistrationSummaryDto summaryDto = LectureRegistrationSummaryDto.builder()
                .lectureId(lecture.getLectureId())
                .subjectName(lecture.getSubject().getSubjectName())
                .teacherName(lecture.getTeacher().getTeacherName())
                .maxEnrollment(lecture.getMaxEnrollment())
                .currentRegisteredCount(totalRegisteredCount)
                .registeredStudents(registeredStudentsDto)
                .build();

        return ResponseDto.setSuccess(ResponseMessage.GET_REGISTERED_STUDENTS_SUCCESS, summaryDto);
    }

    // 강의별 수강 확정 학생 명단 조회 (관리자/교사)
    @Override
    public ResponseDto<List<EnrolledStudentListDto>> getEnrolledStudentsByLectureId(String username, Long lectureId) {
        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        boolean lectureExists = lectureRepository.existsById(lectureId);
        if (!lectureExists) {
            throw new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LECTURE + ": " + lectureId);
        }

        List<CourseRegistration> confirmedRegistrations = courseRegistrationRepository.findByLecture_LectureIdAndStatusConfirmedWithStudent(lectureId);

        List<EnrolledStudentListDto> responseData = confirmedRegistrations.stream()
                .map(registration -> {
                    Student student = registration.getStudent(); // JOIN FETCH로 이미 가져왔으므로 N+1 발생 안함
                    return EnrolledStudentListDto.builder()
                            .studentId(student.getStudentId())
                            .studentNumber(student.getStudentNumber())
                            .studentName(student.getStudentName())
                            .studentGrade(student.getStudentGrade())
                            .studentClass(student.getStudentClass())
                            .studentBirthDate(student.getStudentBirthDate())
                            .build();
                })
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.GET_ENROLLED_STUDENTS_SUCCESS, responseData);
    }
}
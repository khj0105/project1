package com.example.project1.service.impl;

import com.example.project1.common.enums.ErrorCode;
import com.example.project1.common.exception.CustomException;
import com.example.project1.dto.lecture.response.LectureDetailResponseDto;
import com.example.project1.dto.lecture.response.LectureSimpleResponseDto;
import com.example.project1.dto.registration.request.CourseRegistrationRequestDto;
import com.example.project1.dto.registration.response.CourseRegistrationResponseDto;
import com.example.project1.entity.CourseRegistration;
import com.example.project1.entity.Lecture;
import com.example.project1.entity.Student;
import com.example.project1.repository.CourseRegistrationRepository;
import com.example.project1.repository.LectureRepository;
import com.example.project1.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    // 전체 강의 목록
    public List<LectureSimpleResponseDto> getAllLectures() {
        List<Lecture> lectures = lectureRepository.findAllWithSubjectAndTeacher();
        return lectures.stream()
                .map(lecture -> LectureSimpleResponseDto.builder()
                        .id(lecture.getLectureId())
                        .subjectName(lecture.getSubject().getSubjectName())
                        .teacherName(lecture.getTeacher().getTeacherName())
                        .dayOfWeek(lecture.getDayOfWeek().toString())
                        .period(lecture.getPeriod())
                        .maxStudents(lecture.getMaxEnrollment())
                        .currentStudents(lecture.getCourseRegistrations().size())
                        .build())
                .collect(Collectors.toList());
    }

    // 강의 상세
    public LectureDetailResponseDto getLectureDetail(Long lectureId) {
        Lecture lecture = lectureRepository.findByIdWithSubjectTeacherClassroom(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));

        return LectureDetailResponseDto.builder()
                .id(lecture.getLectureId())
                .subjectName(lecture.getSubject().getSubjectName())
                .subjectDescription(lecture.getSubject().getDescription())
                .teacherName(lecture.getTeacher().getTeacherName())
                .dayOfWeek(lecture.getDayOfWeek().toString())
                .period(lecture.getPeriod())
                .maxStudents(lecture.getMaxEnrollment())
                .currentStudents(lecture.getCourseRegistrations().size())
                .classroom(lecture.getClassroom().getName())
                .build();
    }

    @Service
    @RequiredArgsConstructor
    public static class CourseRegistrationService {

        private final CourseRegistrationRepository courseRegistrationRepository;
        private final StudentRepository studentRepository;
        private final LectureRepository lectureRepository;

        // 학생 ID로 수강신청
        public void register(String studentId, CourseRegistrationRequestDto request) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

            Lecture lecture = lectureRepository.findById(request.getLectureId())
                    .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

            if (courseRegistrationRepository.existsByStudent_StudentIdAndLecture_LectureId(studentId, request.getLectureId())) {
                throw new CustomException(ErrorCode.ALREADY_REGISTERED);
            }

            int count = courseRegistrationRepository.countByLecture_LectureId(request.getLectureId());
            if (count >= lecture.getMaxEnrollment()) {
                throw new CustomException(ErrorCode.LECTURE_FULL);
            }

            CourseRegistration registration = CourseRegistration.builder()
                    .student(student)
                    .lecture(lecture)
                    .build();

            courseRegistrationRepository.save(registration);
        }

        // 이메일로 수강신청
        public void registerByEmail(String email, CourseRegistrationRequestDto request) {
            Student student = studentRepository.findByStudentEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

            Lecture lecture = lectureRepository.findById(request.getLectureId())
                    .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

            if (courseRegistrationRepository.existsByStudent_StudentIdAndLecture_LectureId(student.getStudentId(), lecture.getLectureId())) {
                throw new CustomException(ErrorCode.ALREADY_REGISTERED);
            }

            int count = courseRegistrationRepository.countByLecture_LectureId(lecture.getLectureId());
            if (count >= lecture.getMaxEnrollment()) {
                throw new CustomException(ErrorCode.LECTURE_FULL);
            }

            CourseRegistration registration = CourseRegistration.builder()
                    .student(student)
                    .lecture(lecture)
                    .build();

            courseRegistrationRepository.save(registration);
        }

        public List<CourseRegistrationResponseDto> getMyRegistrations(String email) {
            Student student = studentRepository.findByStudentEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

            List<CourseRegistration> registrations = courseRegistrationRepository.findByStudent_StudentId(student.getStudentId());

            return registrations.stream().map(reg -> {
                Lecture lec = reg.getLecture();
                return CourseRegistrationResponseDto.builder()
                        .registrationId(reg.getId())
                        .lectureId(lec.getLectureId())
                        .lectureName(lec.getSubject().getSubjectName())
                        .subjectName(lec.getSubject().getSubjectName())
                        .teacherName(lec.getTeacher().getTeacherName())
                        .dayOfWeek(lec.getDayOfWeek())
                        .period(lec.getPeriod())
                        .build();
            }).toList();
        }

        public CourseRegistrationResponseDto getRegistrationDetail(String email, Long registrationId) {
            Student student = studentRepository.findByStudentEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

            CourseRegistration reg = courseRegistrationRepository.findByIdAndStudent_StudentId(registrationId, student.getStudentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.REGISTRATION_NOT_FOUND));

            Lecture lec = reg.getLecture();

            return CourseRegistrationResponseDto.builder()
                    .registrationId(reg.getId())
                    .lectureId(lec.getLectureId())
                    .lectureName(lec.getSubject().getSubjectName())
                    .subjectName(lec.getSubject().getSubjectName())
                    .teacherName(lec.getTeacher().getTeacherName())
                    .dayOfWeek(lec.getDayOfWeek())
                    .period(lec.getPeriod())
                    .build();
        }

        public void cancelRegistration(String email, Long registrationId) {
            Student student = studentRepository.findByStudentEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

            CourseRegistration reg = courseRegistrationRepository.findByIdAndStudent_StudentId(registrationId, student.getStudentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.REGISTRATION_NOT_FOUND));

            courseRegistrationRepository.delete(reg);
        }

    }
}

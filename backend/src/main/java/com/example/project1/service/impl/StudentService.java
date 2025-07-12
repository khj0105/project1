package com.example.project1.service.impl;

import com.example.project1.common.enums.StudentStatus;
import com.example.project1.dto.auth.request.LoginRequestDto;
import com.example.project1.dto.student.request.StudentSignUpRequestDto;
import com.example.project1.dto.student.request.StudentUpdateRequestDto;
import com.example.project1.dto.student.response.StudentInfoResponseDto;
import com.example.project1.entity.Student;
import com.example.project1.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signUp(StudentSignUpRequestDto request) {
        if (studentRepository.existsByStudentUsername(request.getStudentUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (studentRepository.existsByStudentEmail(request.getStudentEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        LocalDate birth;
        try {
            birth = LocalDate.parse(request.getStudentBirthDate());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("생년월일 형식이 잘못되었습니다. (예: 2006-09-01)");
        }

        Student student = Student.builder()
                .studentId(request.getStudentUsername())
                .studentUsername(request.getStudentUsername())
                .studentPassword(passwordEncoder.encode(request.getStudentPassword()))
                .studentNumber(request.getStudentNumber())
                .studentName(request.getStudentName())
                .studentGrade(request.getStudentGrade())
                .studentEmail(request.getStudentEmail())
                .studentPhoneNumber(request.getStudentPhoneNumber())
                .studentBirthDate(birth)
                .studentAffiliation(request.getStudentAffiliation())
                .studentStatus(StudentStatus.PENDING)
                .studentAdmissionYear(request.getStudentAdmissionYear())
                .build();

        studentRepository.save(student);
    }

    public Student login(LoginRequestDto request) {
        Student student = studentRepository.findByStudentUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(request.getPassword(), student.getStudentPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
            return student;
        }
    // 학생 정보 조회(마이페이지)
    public StudentInfoResponseDto getStudentInfo(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));
        return StudentInfoResponseDto.from(student);
    }
    // 학생 정보 수정(마이페이지)
    public void updateStudentInfo(String studentId, StudentUpdateRequestDto request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        student.update(
                request.getStudentName(),
                request.getStudentPhoneNumber(),
                request.getStudentEmail()
        );

        studentRepository.save(student);
    }



}

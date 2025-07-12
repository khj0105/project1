package com.example.project1.service.impl.student;

import com.example.project1.common.ResponseMessage;
import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.student.response.StudentGetResponseDto;
import com.example.project1.dto.student.response.StudentListGetResponseDto;
import com.example.project1.entity.Student;
import com.example.project1.repository.SchoolRepository;
import com.example.project1.repository.StudentRepository;
import com.example.project1.repository.TeacherRepository;
import com.example.project1.service.StudentManageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentManageServiceImpl implements StudentManageService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseDto<StudentGetResponseDto> findByStudentId(String username, String studentId) {
        StudentGetResponseDto responseData = null;

        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_STUDENT + ": " + studentId));

        responseData = StudentGetResponseDto.builder()
                .studentId(student.getStudentId())
                .studentNumber(student.getStudentNumber())
                .studentName(student.getStudentName())
                .studentClass(student.getStudentClass())
                .studentGrade(student.getStudentGrade())
                .studentEmail(student.getStudentEmail())
                .studentPhoneNumber(student.getStudentPhoneNumber())
                .studentBirthDate(student.getStudentBirthDate())
                .studentAffiliation(student.getStudentAffiliation())
                .studentAdmissionYear(student.getStudentAdmissionYear())
                .studentStatus(student.getStudentStatus())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.GET_STUDENT_DETAIL_SUCCESS, responseData);
    }

    @Override
    public ResponseDto<List<StudentListGetResponseDto>> searchStudents(String username, String studentNumber, String studentName, int studentGrade, int studentClass, SubjectAffiliation studentAffiliation) {


        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        List<Student> students = studentRepository.searchStudents(studentNumber, studentName, studentGrade, studentClass, studentAffiliation);

        List<StudentListGetResponseDto> responseData = students.stream()
                .map(student -> StudentListGetResponseDto.builder()
                        .studentNumber(student.getStudentNumber())
                        .studentName(student.getStudentName())
                        .studentGrade(student.getStudentGrade())
                        .studentClass(student.getStudentClass())
                        .studentEmail(student.getStudentEmail())
                        .studentAffiliation(student.getStudentAffiliation())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.setSuccess(ResponseMessage.GET_STUDENT_LIST_SUCCESS, responseData);
    }
}

package com.example.project1.service.impl.teacher;

import com.example.project1.common.ResponseMessage;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.teacher.TeacherListGetResponseDto;
import com.example.project1.entity.Teacher;
import com.example.project1.repository.SchoolRepository;
import com.example.project1.repository.TeacherRepository;
import com.example.project1.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherManageServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public ResponseDto<List<TeacherListGetResponseDto>> getTeacherList(String username) {

        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        List<Teacher> teachers = teacherRepository.findAll();

        List<TeacherListGetResponseDto> responseData = teachers.stream()
                .map(teacher -> TeacherListGetResponseDto.builder()
                        .teacherUsername(teacher.getTeacherUsername())
                        .teacherName(teacher.getTeacherName())
                        .teacherEmail(teacher.getTeacherEmail())
                        .teacherPhoneNumber(teacher.getTeacherPhoneNumber())
                        .teacherSubject(teacher.getTeacherSubject())
                        .teacherStatus(teacher.getTeacherStatus())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.setSuccess(ResponseMessage.GET_TEACHER_LIST_SUCCESS, responseData);
    }
}

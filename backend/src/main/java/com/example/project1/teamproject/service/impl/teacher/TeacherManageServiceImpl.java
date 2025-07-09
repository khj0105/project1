package com.example.project1.teamproject.service.impl.teacher;

import com.example.project1.teamproject.common.ResponseMessage;
import com.example.project1.teamproject.dto.common.ResponseDto;
import com.example.project1.teamproject.dto.teacher.TeacherListGetResponseDto;
import com.example.project1.teamproject.entity.Teacher;
import com.example.project1.teamproject.repository.SchoolRepository;
import com.example.project1.teamproject.repository.TeacherRepository;
import com.example.project1.teamproject.service.TeacherService;
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

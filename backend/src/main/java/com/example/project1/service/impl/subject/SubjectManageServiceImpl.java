package com.example.project1.service.impl.subject;

import com.example.project1.common.ResponseMessage;
import com.example.project1.common.enums.SubjectAffiliation;
import com.example.project1.common.enums.SubjectStatus;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.subject.response.SubjectGetResponseDto;
import com.example.project1.dto.subject.response.SubjectListGetResponseDto;
import com.example.project1.entity.Subject;
import com.example.project1.repository.SchoolRepository;
import com.example.project1.repository.SubjectRepository;
import com.example.project1.repository.TeacherRepository;
import com.example.project1.teamproject.repository.*;
import com.example.project1.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectManageServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseDto<SubjectGetResponseDto> getSubjectById(String username, String subjectId) {

        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_SUBJECT + ": " + subjectId));

        SubjectGetResponseDto responseData = SubjectGetResponseDto.builder()
                .subjectId(subject.getSubjectId())
                .schoolId(subject.getSchool().getId())
                .subjectName(subject.getSubjectName())
                .grade(subject.getGrade())
                .semester(subject.getSemester())
                .affiliation(subject.getAffiliation())
                .status(subject.getStatus())
                .maxEnrollment(subject.getMaxEnrollment())
                .build();
        return ResponseDto.setSuccess(ResponseMessage.GET_SUBJECT_DETAIL_SUCCESS, responseData);
    }

    @Override
    public ResponseDto<List<SubjectListGetResponseDto>> searchSubjects(String username, String subjectName, String grade, String semester, SubjectAffiliation affiliation) {
        List<SubjectListGetResponseDto> datas = null;

        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        List<Subject> subjects = subjectRepository.searchSubjects(subjectName, grade, semester, affiliation);

        List<SubjectListGetResponseDto> responseDtos = subjects.stream()
                .map(subject -> SubjectListGetResponseDto.builder()
                        .subjectId(subject.getSubjectId())
                        .subjectName(subject.getSubjectName())
                        .grade(subject.getGrade())
                        .semester(subject.getSemester())
                        .affiliation(subject.getAffiliation())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.GET_SUBJECT_LIST_SUCCESS, responseDtos);
    }

    @Override
    @Transactional
    public ResponseDto<SubjectGetResponseDto> updateSubjectStatus(String username, String subjectId, SubjectStatus newStatus) {
        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_SUBJECT + ": " + subjectId));

        subject.updateStatus(newStatus);
        Subject updatedSubject = subjectRepository.save(subject);


        SubjectGetResponseDto responseData = SubjectGetResponseDto.builder()
                .subjectId(updatedSubject.getSubjectId())
                .schoolId(updatedSubject.getSchool().getId())
                .subjectName(updatedSubject.getSubjectName())
                .grade(updatedSubject.getGrade())
                .semester(updatedSubject.getSemester())
                .affiliation(updatedSubject.getAffiliation())
                .status(updatedSubject.getStatus())
                .maxEnrollment(updatedSubject.getMaxEnrollment())
                .build();
        return ResponseDto.setSuccess(ResponseMessage.UPDATE_SUBJECT_STATUS_SUCCESS, responseData);
    }
}

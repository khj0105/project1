package com.example.project1.service.impl.lecture;

import com.example.project1.common.ResponseMessage;
import com.example.project1.common.enums.LectureDayOfWeek;
import com.example.project1.common.enums.SubjectStatus;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.lecture.request.LectureUpdateRequestDto;
import com.example.project1.dto.lecture.response.LectureDetailDto;
import com.example.project1.dto.lecture.response.LectureListDto;
import com.example.project1.entity.Lecture;
import com.example.project1.entity.Subject;
import com.example.project1.entity.Teacher;
import com.example.project1.repository.LectureRepository;
import com.example.project1.repository.SchoolRepository;
import com.example.project1.repository.SubjectRepository;
import com.example.project1.repository.TeacherRepository;
import com.example.project1.service.LectureService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureManageServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<LectureListDto>> searchLectures(String username,Long lectureId, String subjectName, String teacherName, LectureDayOfWeek dayOfWeek, int period, int allowedGrade) {
        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        List<Lecture> lectures = lectureRepository.searchLectures(
                lectureId, subjectName, teacherName, dayOfWeek, period, allowedGrade
        );

        List<LectureListDto> dto = lectures.stream()
                .map(lecture -> LectureListDto.builder()
                        .lectureId(lecture.getLectureId())
                        .subjectName(lecture.getSubject().getSubjectName())
                        .teacherName(lecture.getTeacher().getTeacherName())
                        .dayOfWeek(lecture.getDayOfWeek())
                        .period(lecture.getPeriod())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.GET_LECTURE_LIST_SUCCESS, dto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<LectureDetailDto> findByStudentId(String username, Long lectureId) {
        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LECTURE + ": " + lectureId));

        LectureDetailDto dto = LectureDetailDto.builder()
                .lectureId(lecture.getLectureId())
                .subjectName(lecture.getSubject().getSubjectName())
                .teacherName(lecture.getTeacher().getTeacherName())
                .dayOfWeek(lecture.getDayOfWeek())
                .period(lecture.getPeriod())
                .grade(lecture.getAllowedGrade())
                .maxEnrollment(lecture.getMaxEnrollment())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.GET_LECTURE_DETAIL_SUCCESS, dto);
    }

    @Override
    @Transactional
    public ResponseDto<LectureListDto> updateLecture(String username, Long lectureId, LectureUpdateRequestDto requestDto) {
        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LECTURE));

        Teacher teacher = teacherRepository.findById(requestDto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_TEACHER));

        lecture.updateInfo(teacher, requestDto);
        lectureRepository.save(lecture);

        LectureListDto responseData = LectureListDto.builder()
                .lectureId(lecture.getLectureId())
                .subjectName(lecture.getSubject().getSubjectName())
                .teacherName(lecture.getTeacher().getTeacherName())
                .dayOfWeek(lecture.getDayOfWeek())
                .period(lecture.getPeriod())
                .allowedGrade(lecture.getAllowedGrade())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.UPDATE_LECTURE_SUCCESS, responseData);
    }

    @Override
    public ResponseDto<?> deleteLecture(String username, Long lectureId) {
        boolean isAuthorized = schoolRepository.existsBySchoolCode(username)
                || teacherRepository.existsByTeacherUsername(username);

        if (!isAuthorized) {
            throw new RuntimeException(ResponseMessage.NO_AUTHORITY + ": " + username);
        }

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_LECTURE));

        Subject subject = lecture.getSubject();
        subject.updateStatus(SubjectStatus.DELETED);
        subjectRepository.save(subject);

        lectureRepository.delete(lecture);

        return ResponseDto.setSuccess(ResponseMessage.DELETE_LECTURE_SUCCESS, null);
    }
}
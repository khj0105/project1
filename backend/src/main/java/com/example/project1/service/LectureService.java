package com.example.project1.service;

import com.example.project1.common.enums.LectureDayOfWeek;
import com.example.project1.dto.common.ResponseDto;
import com.example.project1.dto.lecture.request.LectureUpdateRequestDto;
import com.example.project1.dto.lecture.response.LectureDetailDto;
import com.example.project1.dto.lecture.response.LectureListDto;

import java.util.List;

public interface LectureService {
    ResponseDto<List<LectureListDto>> searchLectures(String username, Long lectureId, String subjectName, String teacherName, LectureDayOfWeek dayOfWeek, int period, int allowedGrade);
    ResponseDto<LectureDetailDto> findByStudentId(String username, Long lectureId);
    ResponseDto<LectureListDto> updateLecture(String username, Long lectureId, LectureUpdateRequestDto requestDto);
    ResponseDto<?> deleteLecture(String username, Long lectureId);
}
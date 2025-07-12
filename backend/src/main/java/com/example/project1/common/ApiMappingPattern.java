package com.example.project1.common;

public class ApiMappingPattern {
    // === RESTful API === //
    // 공통: 로그인한 사용자 전체 (학생, 교사, 관리자)
    public static final String API_COMMON = "/api/v1/common";

    // 권한, 인증 없는 경로
    public static final String API_AUTH_COMMON = "/api/v1/auth/common";

    // 인증 관련 (각 권한 별 사용)
    public static final String API_AUTH_ADMIN = "/api/v1/auth/admin";
    public static final String API_AUTH_TEACHER = "/api/v1/auth/teacher";
    public static final String API_AUTH_STUDENT = "/api/v1/auth/student";

    // 관리자 전용
    public static final String API_ADMIN = "/api/v1/admin";
    public static final String API_ADMIN_SUBJECT = API_ADMIN + "/subjects";
    public static final String API_ADMIN_SUBJECT_STATUS = API_ADMIN_SUBJECT + "/{subjectId}/status";
    public static final String API_ADMIN_LECTURE = API_ADMIN + "lectures";
    public static final String API_ADMIN_LECTURE_DETAIL = API_ADMIN_LECTURE + "/{lectureId}";

    // 교사 전용
    public static final String API_TEACHER = "/api/v1/teacher";

    // 학생 전용
    public static final String API_STUDENT = "/api/v1/student";

    // 과목 관련
    public static final String SUBJECT_API = "/api/v1/subjects";

    // 강의 관련
    public static final String LECTURE_API = "/api/v1/lectures";

    // 관리자 + 교사 공통 기능
    public static final String API_MANAGE = "/api/v1/manage";
    public static final String API_MANAGE_STUDENT = API_MANAGE + "/students";
    public static final String API_MANAGE_TEACHER = API_MANAGE +  "/teachers";
    public static final String API_MANAGE_SUBJECT = API_MANAGE +  "/subjects";
    public static final String API_MANAGE_LECTURE = API_MANAGE +  "/lectures";
    public static final String API_MANAGE_LECTURE_REGISTRATIONS_STUDENTS = API_MANAGE_LECTURE + "/{lectureId}/registrations-students";
    public static final String API_MANAGE_LECTURE_ENROLLED_STUDENTS = API_MANAGE_LECTURE + "/{lectureId}/enrolled-students";

    // 교사 + 학생 공통 기능
    public static final String API_CLASSROOM = "/api/v1/classroom";

    // 로그인 후 (인증된 사용자 API) 일 경우 기능에 맞게 api 작성 (admin, teacher, student 첨부 X)
    // + 관리자-교사 / 교사-학생 공통 권한 포함
    // SecurityConfig 에서 권한 부여 (.hasAnyRole, .hasRole)
}
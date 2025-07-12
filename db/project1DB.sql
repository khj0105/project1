CREATE DATABASE IF NOT EXISTS `high_school_credit_system_webpage` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `high_school_credit_system_webpage`;

-- 학교 승인 대기 테이블
CREATE TABLE IF NOT EXISTS `school_application`(
	school_application_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    school_application_status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    school_name VARCHAR(30) NOT NULL,
    school_address VARCHAR(255) NOT NULL,
    school_email VARCHAR(30) NOT NULL,
    school_contact_number VARCHAR(20) NOT NULL,
    school_admin_name VARCHAR(30) NOT NULL,
    school_admin_phone_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 학교 계정 자격 발송 이메일 테이블
CREATE TABLE IF NOT EXISTS `school_credential_issuance`(
	school_credential_issuance_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_application_id BIGINT NOT NULL UNIQUE,
    school_code VARCHAR(30) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (school_application_id) REFERENCES school_application(school_application_id)
);

-- 학교 테이블
CREATE TABLE IF NOT EXISTS `school` (
	school_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    school_name VARCHAR(30) NOT NULL,
    school_address VARCHAR(255) NOT NULL,
    school_contact_number VARCHAR(30) NOT NULL,
    school_code VARCHAR(30) NOT NULL,
    application_started_day DATE NOT NULL,
    application_limited_day DATE NOT NULL,
    deleted_at DATETIME DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `school_policy`(
	policy_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    school_id BIGINT NOT NULL UNIQUE,
    max_credits_semester BIGINT NOT NULL,
    graduation_credits BIGINT NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (school_id) REFERENCES `school`(school_id)
);

-- user 테이블
CREATE TABLE IF NOT EXISTS `user`(
	user_id BIGINT PRIMARY KEY auto_increment,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL,
    name VARCHAR(30) NOT NULL,
    user_type ENUM('SUPER_ADMIN', 'SCHOOL_ADMIN', 'TEACHER', 'STUDENT') NOT NULL,
    school_id BIGINT,
    user_status ENUM('ACTIVE', 'INACTIVE', 'PENDING') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (school_id) REFERENCES school(school_id)
);

-- USER-교사 테이블
CREATE TABLE IF NOT EXISTS `teacher_details`(
	user_id BIGINT PRIMARY KEY,
    subject VARCHAR(10) NOT NULL,
    teacher_status ENUM('PENDING', 'APPROVED', 'ON_LEAVE', 'RETIRED') DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- USER-학생 테이블
CREATE TABLE IF NOT EXISTS `student_details`(
	user_id BIGINT PRIMARY KEY,
	student_number VARCHAR(30) NOT NULL UNIQUE,
    student_grade BIGINT NOT NULL,
    student_classroom BIGINT NOT NULL,
    student_birth_date DATE NOT NULL,
    student_affiliation ENUM('LIBERAL_ARTS', 'NATURAL_SCIENCES') NOT NULL,
    student_status ENUM('PENDING', 'APPROVED', 'REJECTED', 'ENROLLED', 'GRADUATED') DEFAULT 'PENDING',
    student_admission_year YEAR NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- 과목 테이블: 강의 개설용 정보
CREATE TABLE IF NOT EXISTS `subject`(
	subject_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    school_id BIGINT NOT NULL,
    subject_name VARCHAR(50) NOT NULL,
    subject_grade BIGINT NOT NULL,
    subject_semester BIGINT NOT NULL,
    subject_type ENUM('REQUIRED_SUBJECT','ELECTIVE_COURSES'),
    subject_credits BIGINT NOT NULL,
    subject_affiliation ENUM('LIBERAL_ARTS', 'NATURAL_SCIENCES', 'COMMON') NOT NULL,
    subject_status ENUM('APPROVED', 'PENDING', 'REJECTED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (school_id) REFERENCES school(school_id)
);

-- 강의 테이블 : 시간표 포함, 과목 실제 운영 
CREATE TABLE IF NOT EXISTS `course`(
	course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    school_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    year YEAR NOT NULL,
    semester BIGINT NOT NULL,
    course_max_enrollment INT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
    FOREIGN KEY (teacher_id) REFERENCES user(user_id),
    FOREIGN KEY (school_id) REFERENCES school(school_id)
);

-- 강의 시간표 테이블
CREATE TABLE IF NOT EXISTS `course_schedule`(
	schedule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    day_of_week ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY') NOT NULL,
    period BIGINT NOT NULL,
    location VARCHAR(30),
    FOREIGN KEY (course_id) REFERENCES `course`(course_id) ON DELETE CASCADE
);

-- 수강 신청 및 수강 이력 테이블
CREATE TABLE IF NOT EXISTS `course_enrollment`(
	enrollment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    approval_status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    approval_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enrollment_status ENUM('ENROLLED', 'COMPLETED', 'WITHDRAWN')  NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES user(user_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
);

-- 공지사항 테이블
CREATE TABLE IF NOT EXISTS `notice`(
	notice_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    school_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    notice_title VARCHAR(255) NOT NULL,
    notice_content TEXT NOT NULL,
    notice_target_audience ENUM('ALL', 'STUDENT', 'TEACHER') NOT NULL,
    notice_start_date DATE NOT NULL,
    notice_end_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (school_id) REFERENCES school(school_id),
    FOREIGN KEY (author_id) REFERENCES user(user_id)
);
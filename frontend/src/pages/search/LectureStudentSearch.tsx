import React, { useState, useEffect } from 'react';
import axios from 'axios';
import LectureListItem from '../subject-lecture/Lecture-list'; 
import StudentListModal, { type StudentRegistration } from './StudentListModal';
import './search-common.css';

import { type Lecture as BaseLecture } from '../subject-lecture/Lecture-list';
interface SearchLecture extends BaseLecture {}

const LectureStudentSearch: React.FC = () => {
    const [lectures, setLectures] = useState<SearchLecture[]>([]);
    const [searchId, setSearchId] = useState('');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalTitle, setModalTitle] = useState('');
    const [modalStudents, setModalStudents] = useState<StudentRegistration[]>([]);

    useEffect(() => {
        const fetchLectures = async () => {
            const mockData: SearchLecture[] = [
                { id: 1, 
                lectureCode: 'MATH-101', 
                name: '심화 수학', 
                teacherName: '김선생', 
                status: 'approved', 
                submissionDate: '2024-01-10', 
                grade: '2', 
                semester: '1', 
                track: '이과', 
                applicantCount: 28, 
                maxStudents: 30 },
            ];
            setLectures(mockData);
        };
        fetchLectures();
    }, []);

    const handleSearch = () => {
        console.log("검색할 강의 번호:", searchId);
    };

    const handleOpenModal = (lectureId: number | string, type: 'registrations' | 'enrolled') => {
        const currentLecture = lectures.find(l => l.id === lectureId);
        if (!currentLecture) return;

        let title = '';
        let students: StudentRegistration[] = [];

        if (type === 'registrations') {
            title = `${currentLecture.name} - 신청자 명단`;
            students = [
                {   studentId: 'S001', 
                    studentNumber: '20250101', 
                    studentName: '박대기', 
                    studentGrade: 2, 
                    studentClass: 3, 
                    studentEmail: 'park@example.com', 
                    status: '수강 대기' 
                },
                {   studentId: 'S002', 
                    studentNumber: '20250102', 
                    studentName: '이신청', 
                    studentGrade: 2, 
                    studentClass: 1, 
                    studentEmail: 'lee@example.com', 
                    status: '수강 대기' 
                },
            ];
        } else {
            title = `${currentLecture.name} - 확정자 명단`;
            students = [
                {   studentId: 'S003', 
                    studentNumber: '20250201', 
                    studentName: '김확정', 
                    studentGrade: 2, 
                    studentClass: 3, 
                    studentEmail: 'kim@example.com', 
                    status: '수강 확정' 
                },
            ];
        }
        
        setModalTitle(title);
        setModalStudents(students);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    return (
        <div className="search-tab-content">
            <div className="filter-container" style={{ marginBottom: '24px' }}>
                <input 
                    type="text"
                    placeholder="강의 번호로 검색" 
                    className="filter-input" 
                    style={{ minWidth: '300px' }}
                    value={searchId}
                    onChange={(e) => setSearchId(e.target.value)}
                />
                <button className="search-button" onClick={handleSearch}>검색</button>
            </div>
            <div className="lecture-list-container">
                {lectures.map((lecture) => (
                    <LectureListItem 
                        key={lecture.id} 
                        lecture={lecture} 
                        mode="search" 
                        onClick={handleOpenModal}
                    />
                ))}
            </div>
            <StudentListModal 
                isOpen={isModalOpen}
                onClose={handleCloseModal}
                title={modalTitle}
                students={modalStudents}
            />
        </div>
    );
};

export default LectureStudentSearch;
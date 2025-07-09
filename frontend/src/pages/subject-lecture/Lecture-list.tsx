import React, { useState } from 'react';
import axios from 'axios';
import './Lecture-list.css';

export interface Lecture {
  id: number;
  lectureCode: string; 
  name: string;
  teacherName: string;
  status: 'pending' | 'approved' | 'rejected' | 'deleted';
  submissionDate: string;
  subjectId?: string;
  schoolId?: string;
  grade?: string;
  semester?: string;
  track?: string;
  maxStudents?: number;
  applicantCount?: number;
}

export enum CourseRegistrationStatus {
  WAITING = '수강 대기',
  CONFIRMED = '수강 확정',
  REJECTED = '수강 탈락',
  CANCELED = '수강 취소',
}

export interface StudentRegistrationInfo {
  studentId: string;
  studentNumber: string;
  studentName: string;
  studentGrade: number;
  studentClass: number;
  studentEmail: string;
  status: CourseRegistrationStatus;
}

type OnClickType = (id: number | string, type?: 'registrations' | 'enrolled') => void;

interface LectureListItemProps {
  lecture: Lecture;
  onClick?: OnClickType;
  mode: 'management' | 'search'; 
}

const LectureListItem: React.FC<LectureListItemProps> = ({ lecture, onClick, mode }) => {
  const [studentList, setStudentList] = useState<StudentRegistrationInfo[]>([]);
  const [listTitle, setListTitle] = useState<string>('');
  const [showDetails, setShowDetails] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const getStatusInfo = (status: Lecture['status']) => {
    switch (status) {
      case 'approved': return { text: '승인', className: 'status-approved' };
      case 'rejected': return { text: '거절', className: 'status-rejected' };
      case 'deleted': return { text: '삭제됨', className: 'status-deleted' };
      default: return { text: '대기', className: 'status-pending' };
    }
  };
  const statusInfo = getStatusInfo(lecture.status);
  const itemClassName = `lecture-list-item ${lecture.status === 'deleted' ? 'is-deleted' : ''}`;

  const toggleStudentList = async (type: 'registrations' | 'enrolled') => {
    const desiredTitle = `'${lecture.name}' 강의 ${type === 'registrations' ? '신청자' : '확정자'} 명단`;
    if (showDetails && listTitle === desiredTitle) {
      setShowDetails(false);
      return;
    }

    setIsLoading(true);
    setShowDetails(true);
    setListTitle(desiredTitle);

    try {
      let mockStudents: StudentRegistrationInfo[] = [];
      if (type === 'registrations') {
        mockStudents = [
          { studentId: 'S001', studentNumber: '20250101', studentName: '박대기', studentGrade: 2, studentClass: 3, studentEmail: 'park@example.com', status: CourseRegistrationStatus.WAITING },
          { studentId: 'S002', studentNumber: '20250102', studentName: '이신청', studentGrade: 2, studentClass: 1, studentEmail: 'lee@example.com', status: CourseRegistrationStatus.WAITING },
        ];
      } else {
        mockStudents = [
          { studentId: 'S003', studentNumber: '20250201', studentName: '김확정', studentGrade: 2, studentClass: 3, studentEmail: 'kim@example.com', status: CourseRegistrationStatus.CONFIRMED },
        ];
      }
      setStudentList(mockStudents);
    } catch (error) {
      console.error("Failed to fetch student list", error);
      setListTitle("명단을 불러오는데 실패했습니다.");
    } finally {
      setIsLoading(false);
    }
  };
  
  const handleManagementClick = () => {
    if (mode === 'management' && lecture.status !== 'deleted' && onClick) {
      onClick(lecture.id);
    }
  };

  const managementView = (
    <div className={itemClassName} onClick={handleManagementClick}>
      <div className="item-cell" style={{ flexBasis: '25%', fontWeight: 600 }}>{lecture.name}</div>
      <div className="item-cell" style={{ flexBasis: '15%' }}>{lecture.teacherName}</div>
      <div className="item-cell" style={{ flexBasis: '15%' }}>{lecture.submissionDate}</div>
      <div className="item-cell" style={{ flexBasis: '10%' }}>{lecture.grade}학년</div>
      <div className="item-cell" style={{ flexBasis: '10%' }}>{lecture.semester}학기</div>
      <div className="item-cell" style={{ flexBasis: '10%' }}>{lecture.track}</div>
      <div className="item-cell" style={{ flexBasis: '15%', textAlign: 'center' }}>
        <span className={`status-badge ${statusInfo.className}`}>{statusInfo.text}</span>
      </div>
    </div>
  );

  const searchView = (
    <div className="lecture-card-wrapper">
      <div className="lecture-list-item">
        <div className="item-cell" style={{ flexBasis: '15%', fontWeight: 'bold', color: '#555' }}>{lecture.lectureCode}</div>
        <div className="item-cell" style={{ flexBasis: '25%', fontWeight: 600 }}>{lecture.name}</div>
        <div className="item-cell" style={{ flexBasis: '15%' }}>{lecture.teacherName}</div>
        <div className="item-cell search-mode-actions" style={{ flexBasis: '45%' }}>
          <span>신청: <strong>{lecture.applicantCount || 0} / {lecture.maxStudents || 0}</strong></span>
          <div className="button-group">
            <button onClick={() => toggleStudentList('registrations')}>신청자</button>
            <button onClick={() => toggleStudentList('enrolled')}>확정자</button>
          </div>
        </div>
      </div>
      {showDetails && (
        <div className="card-details">
          {isLoading ? ( <div>명단 로딩 중...</div> ) : (
            <>
              <h4>{listTitle}</h4>
              <table className="details-table">
                <thead>
                  <tr>
                    <th>학생 아이디</th>
                    <th>학번</th>
                    <th>이름</th>
                    <th>학년</th>
                    <th>반</th>
                    <th>이메일</th>
                    <th>상태</th>
                  </tr>
                </thead>
                <tbody>
                  {studentList.map((student) => (
                    <tr key={student.studentId}>
                      <td>{student.studentId}</td>
                      <td>{student.studentNumber}</td>
                      <td>{student.studentName}</td>
                      <td>{student.studentGrade}</td>
                      <td>{student.studentClass}</td>
                      <td>{student.studentEmail}</td>
                      <td>{student.status}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </>
          )}
        </div>
      )}
    </div>
  );
  
  return mode === 'management' ? managementView : searchView;
};

export default LectureListItem;
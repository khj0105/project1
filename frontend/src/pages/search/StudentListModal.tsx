import React from 'react';
import './StudentListModal.css';

export interface StudentRegistration {
  studentId: string;
  studentNumber: string;
  studentName: string;
  studentGrade: number;
  studentClass: number;
  studentEmail: string;
  status: '수강 대기' | '수강 확정' ;
}

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  students: StudentRegistration[];
}

const StudentListModal: React.FC<ModalProps> = ({ isOpen, onClose, title, students }) => {
  if (!isOpen) {
    return null;
  }

  return (
    <div className="modal-backdrop-search">
      <div className="modal-content-search">
        <div className="modal-header-search">
          <h2>{title}</h2>
          <button onClick={onClose} className="close-button-search">&times;</button>
        </div>
        <div className="modal-body-search">
          <table className="student-list-table">
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
              {students.length > 0 ? (
                students.map((student) => (
                  <tr key={student.studentId}>
                    <td>{student.studentId}</td>
                    <td>{student.studentNumber}</td>
                    <td>{student.studentName}</td>
                    <td>{student.studentGrade}</td>
                    <td>{student.studentClass}</td>
                    <td>{student.studentEmail}</td>
                    <td>{student.status}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={7}>표시할 학생이 없습니다.</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default StudentListModal;
import React, { useState } from 'react';
import axios from 'axios';
import './search-common.css';

interface Student {
  studentId: number; 
  grade: number; 
  class: number; 
  name: string; 
  birthDate: string;
}
interface StudentDetails extends Student {
  phone: string; 
  address: string;
}

interface FilterState {
  track: string;
  studentId: string;
  studentName: string;
  grade: string;
  class: string;
}

const StudentSearch: React.FC = () => {
  const [filters, setFilters] = useState<FilterState>({
    track: '',
    studentId: '',
    studentName: '',
    grade: '',
    class: '',
  });

  const [students, setStudents] = useState<Student[]>([]);
  const [selectedStudent, setSelectedStudent] = useState<StudentDetails | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFilters(prevFilters => ({
      ...prevFilters,
      [name]: value,
    }));
  };

  const handleSearch = () => {
    console.log("검색 필터:", filters); 
    
    const mockData: Student[] = [
      { studentId: 101, grade: 1, class: 3, name: '김학생', birthDate: '2007-03-15' },
      { studentId: 102, grade: 1, class: 3, name: '이학생', birthDate: '2007-05-20' },
    ];
    setStudents(mockData);
  };

  const handleViewDetails = async (studentId: number) => {
    const mockDetail: StudentDetails = {
      studentId: 101, name: '김학생', grade: 1, class: 3, birthDate: '2007-03-15',
      phone: '010-1111-2222', address: '부산시 해운대구'
    };
    setSelectedStudent(mockDetail);
    setIsModalOpen(true);
  };

  return (
    <div className="search-tab-content">
      <div className="filter-container">
        <select name="track" value={filters.track} onChange={handleFilterChange} className="filter-input">
          <option value="">계열 전체</option>
          <option value="humanities">인문계열</option>
          <option value="natural_sciences">자연계열</option>
        </select>
        <input 
          type="text"
          name="studentId"
          placeholder="학번으로 검색" 
          value={filters.studentId} 
          onChange={handleFilterChange} 
          className="filter-input"
        />
        <input 
          type="text"
          name="studentName"
          placeholder="학생 이름으로 검색" 
          value={filters.studentName} 
          onChange={handleFilterChange} 
          className="filter-input"
        />
        <select name="grade" value={filters.grade} onChange={handleFilterChange} className="filter-input">
          <option value="">학년 전체</option>
          <option value="1">1학년</option>
          <option value="2">2학년</option>
          <option value="3">3학년</option>
        </select>
        <select name="class" value={filters.class} onChange={handleFilterChange} className="filter-input">
          <option value="">반 전체</option>
          <option value="1">1반</option>
          <option value="2">2반</option>
          <option value="3">3반</option>
          <option value="4">4반</option>
          <option value="5">5반</option>
          <option value="6">6반</option>
          <option value="7">7반</option>
        </select>
        <button onClick={handleSearch} className="search-button">검색</button>
      </div>

      <table className="data-table student-table">
        <thead>
          <tr><th>학년</th><th>반</th><th>이름</th><th>생년월일</th></tr>
        </thead>
        <tbody>
          {students.map((student) => (
            <tr key={student.studentId} onClick={() => handleViewDetails(student.studentId)}>
              <td>{student.grade}</td>
              <td>{student.class}</td>
              <td>{student.name}</td>
              <td>{student.birthDate}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {isModalOpen && selectedStudent && (
        <div className="modal-backdrop">
          <div className="modal-content">
            <h2>학생 상세 정보</h2>
            <div className="details-card">
              <div className="detail-item">
                <strong>이름:</strong> {selectedStudent.name}
              </div>
              <div className="detail-item">
                <strong>학년/반:</strong> 
                {selectedStudent.grade}학년 
                {selectedStudent.class}반
              </div>
              <div className="detail-item">
                <strong>생년월일:</strong> {selectedStudent.birthDate}
              </div>
              <div className="detail-item">
                <strong>연락처:</strong> {selectedStudent.phone}
              </div>
              <div className="detail-item">
                <strong>주소:</strong> {selectedStudent.address}
              </div>
            </div>
            <div className="modal-actions">
              <button onClick={() => setIsModalOpen(false)} className="close-button">
                닫기
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default StudentSearch;
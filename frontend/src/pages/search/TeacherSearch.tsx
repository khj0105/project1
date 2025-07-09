import React, { useState } from 'react';
import axios from 'axios';
import './search-common.css'; 

interface Teacher {
  id: number;
  name: string;
  subject: string;
  phone: string;
}

const TeacherSearchPage: React.FC = () => {
  const [teachers, setTeachers] = useState<Teacher[]>([]);

  return (
    <div className="search-tab-content">
      <div className="filter-container">
        <input placeholder="교사 이름으로 검색" className="filter-input" />
        <button className="search-button">검색</button>
      </div>
      <table className="data-table">
        <thead>
          <tr>
            <th>이름</th>
            <th>담당 과목</th>
            <th>연락처</th>
          </tr>
        </thead>
        <tbody>
          {teachers.map((teacher) => (
            <tr key={teacher.id}>
              <td>{teacher.name}</td>
              <td>{teacher.subject}</td>
              <td>{teacher.phone}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TeacherSearchPage;
import React, { useState } from 'react';
import TeacherSearch from './TeacherSearch';
import StudentSearch from './StudentSearch';
import LectureStudentSearch from './LectureStudentSearch';
import './UnifiedSearch.css';

type SearchTab = 'teachers' | 'students' | 'lectures';

const UnifiedSearchPage: React.FC = () => {
  const [activeTab, setActiveTab] = useState<SearchTab>('teachers');

  const renderContent = () => {
    switch (activeTab) {
      case 'teachers':
        return <TeacherSearch />;
      case 'students':
        return <StudentSearch />;
      case 'lectures':
        return <LectureStudentSearch />;
      default:
        return <TeacherSearch />;
    }
  };

  return (
    <div className="search-page-container">
      <h1 className="search-page-title">통합 조회</h1>

      <div className="search-tabs">
        <button
          className={`tab-button ${activeTab === 'teachers' ? 'active' : ''}`}
          onClick={() => setActiveTab('teachers')}
        >
          교사 조회
        </button>
        <button
          className={`tab-button ${activeTab === 'students' ? 'active' : ''}`}
          onClick={() => setActiveTab('students')}
        >
          학생 조회
        </button>
        <button
          className={`tab-button ${activeTab === 'lectures' ? 'active' : ''}`}
          onClick={() => setActiveTab('lectures')}
        >
          강의별 학생 조회
        </button>
      </div>

      <div className="search-content">
        {renderContent()}
      </div>
    </div>
  );
};

export default UnifiedSearchPage;
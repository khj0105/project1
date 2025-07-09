import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import LectureListItem, { type Lecture as LectureData } from './Lecture-list';
import LectureFormModal, { type NewLectureData } from './LectureFormModal';
import ListHeader from './ListHeader';
import FilterBar from './FilterBar';
import './LectureManagementPage.css';

const getInitialLectures = (): LectureData[] => {
  const savedLectures = localStorage.getItem('myLectures');
  if (savedLectures) {
    return JSON.parse(savedLectures);
  }
  return [
    { id: 1, lectureCode: 'KOR-001', name: '국어', teacherName: '홍선생', submissionDate: '2025-06-26', grade: '1', semester: '1', track: '공통', status: 'pending', maxStudents: 30 },
  ];
};

const LectureList: React.FC<{ lectures: LectureData[]; onCardClick: (id: number) => void }> = ({ lectures, onCardClick }) => {
  return (
    <div className="lecture-list-container">
      {lectures.length > 0 ? (
        lectures.map(lecture => (
          <LectureListItem 
            key={lecture.id} 
            lecture={lecture} 
            onClick={onCardClick as any}
            mode="management"
          />
        ))
      ) : (
        <p className="empty-list-message">표시할 과목이 없습니다.</p>
      )}
    </div>
  );
};

const LectureManagementPage: React.FC = () => {
  const [activeTab, setActiveTab] = useState('proposals');
  const [isModalOpen, setIsModalOpen] = useState(false);
  
  const [lectures, setLectures] = useState<LectureData[]>(getInitialLectures);
  
  const navigate = useNavigate();
  const location = useLocation();
  const currentUser = { role: 'teacher', name: '홍선생' };

  useEffect(() => {
    if (location.state?.activeTab) {
      setActiveTab(location.state.activeTab);
    }
  }, [location.state]);

  useEffect(() => {
    localStorage.setItem('myLectures', JSON.stringify(lectures));
  }, [lectures]);

  const handleCardClick = (id: number) => {
    navigate(`/lecture-management/${id}`);
  };
  
  const handleAddLecture = (newLectureData: NewLectureData) => {
    const newLecture: LectureData = {
      id: Date.now(),
      lectureCode: `${newLectureData.track.substring(0,3).toUpperCase()}-${Math.floor(Math.random()*1000)}`,
      name: newLectureData.name,
      teacherName: currentUser.name,
      status: 'pending',
      submissionDate: new Date().toISOString().split('T')[0],
      subjectId: newLectureData.subjectId,
      schoolId: newLectureData.schoolId,
      grade: newLectureData.grade,
      semester: newLectureData.semester,
      track: newLectureData.track,
      maxStudents: newLectureData.maxStudents,
    };
    setLectures(prevLectures => [newLecture, ...prevLectures]);
  };

  const proposalLectures = useMemo(() => 
    lectures.filter(lecture => lecture.status !== 'approved'), 
  [lectures]);

  const approvedLectures = useMemo(() => 
    lectures.filter(lecture => lecture.status === 'approved'), 
  [lectures]);

  return (
    <div className="lecture-management-container">
      <h1 className="page-title">강의 개설 관리</h1>
      <FilterBar />
      <div className="page-actions">
        <div className="tabs">
          <button onClick={() => setActiveTab('proposals')} className={activeTab === 'proposals' ? 'active' : ''}>
            과목 신청 관리
          </button>
          <button onClick={() => setActiveTab('approved')} className={activeTab === 'approved' ? 'active' : ''}>
            승인 강의 조회
          </button>
        </div>
        {currentUser.role === 'teacher' && (
          <button className="add-lecture-button" onClick={() => setIsModalOpen(true)}>+ 새 과목 신청</button>
        )}
      </div>
      <div className="tab-content">
        <ListHeader />
        {activeTab === 'proposals' ? (
          <LectureList lectures={proposalLectures} onCardClick={handleCardClick} />
        ) : (
          <LectureList lectures={approvedLectures} onCardClick={handleCardClick} />
        )}
      </div>
      {isModalOpen && (
        <LectureFormModal 
          mode="create" 
          onClose={() => setIsModalOpen(false)} 
          onSubmit={handleAddLecture}
        />
      )}
    </div>
  );
};

export default LectureManagementPage;
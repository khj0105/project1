import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { type Lecture } from './Lecture-list';
import './LectureManagementDetailPage.css';

const LectureManagementDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [subjectDetails, setSubjectDetails] = useState<Lecture | null>(null);
  const currentUser = { role: 'admin' }; 

  useEffect(() => {
    if (id) {
      const savedLectures = localStorage.getItem('myLectures');
      if(savedLectures) {
          const allLectures: Lecture[] = JSON.parse(savedLectures);
          const currentLecture = allLectures.find(lecture => lecture.id.toString() === id);
          setSubjectDetails(currentLecture || null);
      }
    }
  }, [id]);

  const updateLectureStatus = (newStatus: 'approved' | 'rejected' | 'deleted') => {
    if (!id) return;
    const savedLectures = localStorage.getItem('myLectures');
    if(!savedLectures) return;

    const allLectures: Lecture[] = JSON.parse(savedLectures);
    const updatedLectures = allLectures.map(lecture => 
      lecture.id.toString() === id ? { ...lecture, status: newStatus } : lecture
    );
    localStorage.setItem('myLectures', JSON.stringify(updatedLectures));
  };
  
  const handleApprove = () => {
    updateLectureStatus('approved');
    navigate('/lecture-management', { state: { activeTab: 'approved' } });
  };

  const handleReject = () => {
    updateLectureStatus('rejected');
    navigate('/lecture-management', { state: { activeTab: 'proposals' } });
  };
  
  const handleDelete = () => {
    if (!window.confirm("정말로 이 과목을 삭제 처리하시겠습니까?")) return;
    updateLectureStatus('deleted');
    navigate('/lecture-management', { state: { activeTab: 'proposals' } });
  };
  
  const handleGoToList = () => {
    const targetTab = subjectDetails?.status === 'approved' ? 'approved' : 'proposals';
    navigate('/lecture-management', { state: { activeTab: targetTab } });
  };

  if (!subjectDetails) {
    return <div className="detail-page-container"><p>과목 정보를 불러오는 중입니다...</p></div>;
  }

  return (
    <div className="detail-page-container">
      <h1>과목 상세 정보</h1>
      <div className="details-grid">
        <div className="detail-item"><strong>과목명</strong><span>{subjectDetails.name}</span></div>
        <div className="detail-item"><strong>과목ID</strong><span>{subjectDetails.subjectId}</span></div>
        <div className="detail-item"><strong>신청자</strong><span>{subjectDetails.teacherName}</span></div>
        <div className="detail-item"><strong>학교ID</strong><span>{subjectDetails.schoolId}</span></div>
        <div className="detail-item"><strong>신청일</strong><span>{subjectDetails.submissionDate}</span></div>
        <div className="detail-item"><strong>상태</strong><span className={`status-badge status-${subjectDetails.status}`}>{subjectDetails.status}</span></div>
        <div className="detail-item"><strong>학년</strong><span>{subjectDetails.grade}학년</span></div>
        <div className="detail-item"><strong>학기</strong><span>{subjectDetails.semester}학기</span></div>
        <div className="detail-item"><strong>계열</strong><span>{subjectDetails.track}</span></div>
        <div className="detail-item"><strong>최대수강인원</strong><span>{subjectDetails.maxStudents}명</span></div>
      </div>
      <div className="action-buttons">
        {currentUser.role === 'admin' && subjectDetails.status === 'pending' && (
          <>
            <button onClick={handleApprove}>승인</button>
            <button onClick={handleReject}>거절</button>
          </>
        )}
        {currentUser.role === 'admin' && subjectDetails.status !== 'deleted' && (
            <button onClick={handleDelete}>삭제</button>
        )}
        <button onClick={handleGoToList}>목록으로</button>
      </div>
    </div>
  );
};

export default LectureManagementDetailPage;
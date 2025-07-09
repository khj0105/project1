import React, { useState, useEffect } from 'react';
import './Modal.css';

export interface NewLectureData {
  subjectId: string;
  schoolId: string;
  name: string;
  grade: string;
  semester: string;
  track: string;
  maxStudents: number;
}

interface ModalProps {
  mode: 'create' | 'edit';
  initialData?: any;
  onClose: () => void;
  onSubmit: (data: NewLectureData) => void;
}

const LectureFormModal: React.FC<ModalProps> = ({ mode, initialData, onClose, onSubmit }) => {
  const [subjectId, setSubjectId] = useState('');
  const [schoolId, setSchoolId] = useState('');
  const [courseName, setCourseName] = useState('');
  const [grade, setGrade] = useState('1');
  const [semester, setSemester] = useState('1');
  const [track, setTrack] = useState('문과');
  const [maxStudents, setMaxStudents] = useState(30);

  useEffect(() => {
    if (mode === 'edit' && initialData) {
      setSubjectId(initialData.subjectId || '');
      setSchoolId(initialData.schoolId || '');
      setCourseName(initialData.name || '');
      setGrade(initialData.grade || '1');
      setSemester(initialData.semester || '1');
      setTrack(initialData.track || '문과');
      setMaxStudents(initialData.maxStudents || 30);
    }
  }, [mode, initialData]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const formData: NewLectureData = {
      subjectId,
      schoolId,
      name: courseName,
      grade,
      semester,
      track,
      maxStudents,
    };
    onSubmit(formData);
    onClose();
  };

  return (
    <div className="modal-backdrop">
      <div className="modal-content">
        <h2>{mode === 'create' ? '새 과목 신청' : '과목 정보 수정'}</h2>
        <form onSubmit={handleSubmit} className="course-form">
          <div className="form-row">
            <label htmlFor="subjectId">과목 ID:</label>
            <input id="subjectId" type="text" value={subjectId} onChange={(e) => setSubjectId(e.target.value)} required />
          </div>
          <div className="form-row">
            <label htmlFor="schoolId">학교 ID:</label>
            <input id="schoolId" type="text" value={schoolId} onChange={(e) => setSchoolId(e.target.value)} required />
          </div>
          <div className="form-row">
            <label htmlFor="courseName">과목명:</label>
            <input id="courseName" type="text" value={courseName} onChange={(e) => setCourseName(e.target.value)} required />
          </div>
          <div className="form-row">
            <label htmlFor="grade">학년:</label>
            <select id="grade" value={grade} onChange={(e) => setGrade(e.target.value)}>
              <option value="1">1학년</option>
              <option value="2">2학년</option>
              <option value="3">3학년</option>
            </select>
          </div>
          <div className="form-row">
            <label htmlFor="semester">학기:</label>
            <select id="semester" value={semester} onChange={(e) => setSemester(e.target.value)}>
              <option value="1">1학기</option>
              <option value="2">2학기</option>
            </select>
          </div>
          <div className="form-row">
            <label htmlFor="track">과목 계열:</label>
            <select id="track" value={track} onChange={(e) => setTrack(e.target.value)}>
              <option value="문과">문과</option>
              <option value="이과">이과</option>
              <option value="공통">공통</option>
            </select>
          </div>
          <div className="form-row">
            <label htmlFor="maxStudents">최대 수강 인원:</label>
            <input id="maxStudents" type="number" value={maxStudents} onChange={(e) => setMaxStudents(parseInt(e.target.value, 10))} required />
          </div>
          <div className="modal-actions">
            <button type="submit" className="save-button">등록</button>
            <button type="button" onClick={onClose} className="cancel-button">취소</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LectureFormModal;
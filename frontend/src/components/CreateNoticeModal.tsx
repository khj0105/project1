// src/components/CreateNoticeModal.tsx
import React, { useState, useEffect } from 'react';
import type { Notice, NoticeFormData } from '../types/notice';

interface CreateNoticeModalProps {
  isOpen: boolean;
  editNotice?: Notice | null;
  onClose: () => void;
  onSubmit: (formData: NoticeFormData) => void;
}

const CreateNoticeModal: React.FC<CreateNoticeModalProps> = ({
  isOpen,
  editNotice,
  onClose,
  onSubmit
}) => {
  const [formData, setFormData] = useState<NoticeFormData>({
    notice_title: '',
    notice_content: '',
    notice_target_audience: 'ALL',
    notice_start_date: '',
    notice_end_date: ''
  });

  const [errors, setErrors] = useState<Partial<NoticeFormData>>({});

  // 수정 모드일 때 폼 데이터 설정
  useEffect(() => {
    if (editNotice) {
      setFormData({
        notice_title: editNotice.notice_title,
        notice_content: editNotice.notice_content,
        notice_target_audience: editNotice.notice_target_audience,
        notice_start_date: editNotice.notice_start_date,
        notice_end_date: editNotice.notice_end_date
      });
    } else {
      // 새로 작성할 때는 폼 초기화
      setFormData({
        notice_title: '',
        notice_content: '',
        notice_target_audience: 'ALL',
        notice_start_date: '',
        notice_end_date: ''
      });
    }
    setErrors({});
  }, [editNotice, isOpen]);

  if (!isOpen) return null;

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    
    // 에러 상태 클리어
    if (errors[name as keyof NoticeFormData]) {
      setErrors(prev => ({
        ...prev,
        [name]: undefined
      }));
    }
  };

  const validateForm = (): boolean => {
    const newErrors: Partial<NoticeFormData> = {};

    if (!formData.notice_title.trim()) {
      newErrors.notice_title = '제목을 입력해주세요.';
    }

    if (!formData.notice_content.trim()) {
      newErrors.notice_content = '내용을 입력해주세요.';
    }

    if (!formData.notice_start_date) {
      newErrors.notice_start_date = '게시 시작일을 선택해주세요.';
    }

    if (!formData.notice_end_date) {
      newErrors.notice_end_date = '게시 종료일을 선택해주세요.';
    }

    if (formData.notice_start_date && formData.notice_end_date) {
      if (new Date(formData.notice_start_date) > new Date(formData.notice_end_date)) {
        newErrors.notice_end_date = '종료일은 시작일보다 늦어야 합니다.';
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = () => {
    if (validateForm()) {
      onSubmit(formData);
    }
  };

  const handleBackdropClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div className="modal-backdrop" onClick={handleBackdropClick}>
      <div className="modal-content">
        {/* 헤더 */}
        <div className="modal-header">
          <div className="modal-title-section">
            <h2 className="modal-title">
              {editNotice ? '공지사항 수정' : '새 공지사항 작성'}
            </h2>
          </div>
          <button
            onClick={onClose}
            className="modal-close-btn"
          >
            <svg width="24" height="24" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
        
        {/* 폼 */}
        <div className="form-modal-body">
          {/* 제목 */}
          <div className="form-group">
            <label className="form-label required">
              제목
            </label>
            <input
              type="text"
              name="notice_title"
              value={formData.notice_title}
              onChange={handleInputChange}
              className={`input ${errors.notice_title ? 'error' : ''}`}
              placeholder="공지사항 제목을 입력하세요"
            />
            {errors.notice_title && (
              <p className="error-message">{errors.notice_title}</p>
            )}
          </div>
          
          {/* 대상 */}
          <div className="form-group">
            <label className="form-label required">
              대상
            </label>
            <select 
              name="notice_target_audience"
              value={formData.notice_target_audience}
              onChange={handleInputChange}
              className="select"
            >
              <option value="ALL">전체</option>
              <option value="STUDENT">학생</option>
              <option value="TEACHER">교직원</option>
            </select>
          </div>
          
          {/* 게시 기간 */}
          <div className="form-group">
            <div className="form-grid">
              <div>
                <label className="form-label required">
                  게시 시작일
                </label>
                <input
                  type="date"
                  name="notice_start_date"
                  value={formData.notice_start_date}
                  onChange={handleInputChange}
                  className={`input ${errors.notice_start_date ? 'error' : ''}`}
                />
                {errors.notice_start_date && (
                  <p className="error-message">{errors.notice_start_date}</p>
                )}
              </div>
              <div>
                <label className="form-label required">
                  게시 종료일
                </label>
                <input
                  type="date"
                  name="notice_end_date"
                  value={formData.notice_end_date}
                  onChange={handleInputChange}
                  className={`input ${errors.notice_end_date ? 'error' : ''}`}
                />
                {errors.notice_end_date && (
                  <p className="error-message">{errors.notice_end_date}</p>
                )}
              </div>
            </div>
          </div>
          
          {/* 내용 */}
          <div className="form-group">
            <label className="form-label required">
              내용
            </label>
            <textarea
              name="notice_content"
              rows={8}
              value={formData.notice_content}
              onChange={handleInputChange}
              className={`textarea ${errors.notice_content ? 'error' : ''}`}
              placeholder="공지사항 내용을 입력하세요"
            />
            {errors.notice_content && (
              <p className="error-message">{errors.notice_content}</p>
            )}
          </div>
        </div>
        
        {/* 푸터 */}
        <div className="modal-footer">
          <button
            onClick={onClose}
            className="btn btn-secondary"
          >
            취소
          </button>
          <button 
            onClick={handleSubmit}
            className="btn btn-primary"
          >
            {editNotice ? '수정' : '등록'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default CreateNoticeModal;
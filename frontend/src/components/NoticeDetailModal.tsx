// src/components/NoticeDetailModal.tsx
import React from 'react';
import type { Notice, User } from '../types/notice';

interface NoticeDetailModalProps {
  notice: Notice | null;
  currentUser: User;
  isOpen: boolean;
  onClose: () => void;
  onEdit?: (notice: Notice) => void;
}

const NoticeDetailModal: React.FC<NoticeDetailModalProps> = ({
  notice,
  currentUser,
  isOpen,
  onClose,
  onEdit
}) => {
  if (!isOpen || !notice) return null;

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('ko-KR');
  };

  const getAudienceText = (audience: string) => {
    switch (audience) {
      case 'ALL': return '전체';
      case 'STUDENT': return '학생';
      case 'TEACHER': return '교직원';
      default: return audience;
    }
  };

  const getAudienceBadgeClass = (audience: string) => {
    switch (audience) {
      case 'ALL': return 'badge badge-green';
      case 'STUDENT': return 'badge badge-blue';
      case 'TEACHER': return 'badge badge-purple';
      default: return 'badge';
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
              {notice.notice_title}
            </h2>
            <div className="modal-meta">
              <span className={getAudienceBadgeClass(notice.notice_target_audience)}>
                {getAudienceText(notice.notice_target_audience)}
              </span>
              <span className="modal-date">{formatDate(notice.created_at)}</span>
            </div>
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
        
        {/* 내용 */}
        <div className="modal-body">
          <div className="modal-info-section">
            <h3 className="modal-section-title">게시 기간</h3>
            <p className="modal-section-content">
              {formatDate(notice.notice_start_date)} ~ {formatDate(notice.notice_end_date)}
            </p>
          </div>
          
          <div className="modal-info-section">
            <h3 className="modal-section-title">내용</h3>
            <div className="modal-content-text">
              {notice.notice_content.split('\n').map((line, index) => (
                <p key={index} className="modal-content-line">
                  {line || '\u00A0'} {/* 빈 줄 처리 */}
                </p>
              ))}
            </div>
          </div>
        </div>
        
        {/* 푸터 */}
        <div className="modal-footer">
          <button
            onClick={onClose}
            className="btn btn-secondary"
          >
            닫기
          </button>
          {(currentUser.role === 'ADMIN' || currentUser.role === 'TEACHER') && onEdit && (
            <button 
              onClick={() => onEdit(notice)}
              className="btn btn-primary"
            >
              수정
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default NoticeDetailModal;
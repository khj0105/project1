// src/components/NoticeTable.tsx
import React from 'react';
import { Calendar, Users, Eye, Edit } from 'lucide-react';
import type { Notice, User } from '../types/notice';

interface NoticeTableProps {
  notices: Notice[];
  currentUser: User;
  onNoticeClick: (notice: Notice) => void;
  onEditClick: (notice: Notice) => void;
}

const NoticeTable: React.FC<NoticeTableProps> = ({
  notices,
  currentUser,
  onNoticeClick,
  onEditClick
}) => {
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

  if (notices.length === 0) {
    return (
      <div className="notice-table card">
        <div className="notice-table-header">
          <h2 className="notice-table-title">
            공지사항 목록 (0개)
          </h2>
        </div>
        <div className="notice-empty">
          <div className="notice-empty-icon">
            <Calendar />
          </div>
          <p className="notice-empty-text">검색 결과가 없습니다.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="notice-table card">
      <div className="notice-table-header">
        <h2 className="notice-table-title">
          공지사항 목록 ({notices.length}개)
        </h2>
      </div>

      <div className="notice-list">
        {notices.map((notice) => (
          <div
            key={notice.notice_id}
            className="notice-row"
            onClick={() => onNoticeClick(notice)}
          >
            <div className="notice-content">
              <div className="notice-header">
                <h3 className="notice-title">
                  {notice.notice_title}
                </h3>
                <span className={getAudienceBadgeClass(notice.notice_target_audience)}>
                  {getAudienceText(notice.notice_target_audience)}
                </span>
              </div>
              
              <p className="notice-preview">
                {notice.notice_content.split('\n')[0]}
              </p>
              
              <div className="notice-meta">
                <div className="notice-meta-item">
                  <Calendar className="notice-meta-icon" />
                  <span>{formatDate(notice.notice_start_date)} ~ {formatDate(notice.notice_end_date)}</span>
                </div>
                <div className="notice-meta-item">
                  <Users className="notice-meta-icon" />
                  <span>등록일: {formatDate(notice.created_at)}</span>
                </div>
              </div>
            </div>
            
            <div className="notice-actions">
              <button 
                className="notice-action-btn"
                onClick={(e) => {
                  e.stopPropagation();
                  onNoticeClick(notice);
                }}
              >
                <Eye />
              </button>
              {(currentUser.role === 'ADMIN' || currentUser.role === 'TEACHER') && (
                <button 
                  className="notice-action-btn notice-edit-btn"
                  onClick={(e) => {
                    e.stopPropagation();
                    onEditClick(notice);
                  }}
                >
                  <Edit />
                </button>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default NoticeTable;
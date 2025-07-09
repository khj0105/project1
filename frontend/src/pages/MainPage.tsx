// src/pages/MainPage.tsx
import React, { useState, useEffect } from 'react';
import { Bell, BookOpen, Users, Calendar, TrendingUp, FileText, Award, Clock } from 'lucide-react';
import type { User, Notice } from '../types/notice';
import { getNotices } from '../apis/noticeApi';
import Header from '../components/Header';

const MainPage: React.FC = () => {
  const [recentNotices, setRecentNotices] = useState<Notice[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  // 현재 사용자 (실제로는 인증 컨텍스트에서 가져와야 함)
  const [currentUser] = useState<User>({
    role: 'ADMIN',
    school_id: 1,
    name: '관리자',
    id: 'admin001'
  });

  // 최근 공지사항 로드
  useEffect(() => {
    const loadRecentNotices = async () => {
      try {
        setIsLoading(true);
        const notices = await getNotices();
        // 최근 3개만 표시
        setRecentNotices(notices.slice(0, 3));
      } catch (error) {
        console.error('Failed to load notices:', error);
      } finally {
        setIsLoading(false);
      }
    };

    loadRecentNotices();
  }, []);

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

  // 더미 통계 데이터
  const stats = [
    {
      title: '전체 학생',
      value: '1,248',
      change: '+12%',
      icon: Users,
      color: 'blue'
    },
    {
      title: '진행 중인 강의',
      value: '156',
      change: '+8%',
      icon: BookOpen,
      color: 'green'
    },
    {
      title: '이번 학기 수강신청',
      value: '2,847',
      change: '+15%',
      icon: FileText,
      color: 'purple'
    },
    {
      title: '졸업 예정자',
      value: '89',
      change: '+5%',
      icon: Award,
      color: 'orange'
    }
  ];

  const quickActions = [
    {
      title: '공지사항 관리',
      description: '새 공지사항 작성 및 기존 공지사항 관리',
      icon: Bell,
      href: '/notices',
      color: 'blue'
    },
    {
      title: '강의 관리',
      description: '강의 개설, 수정 및 시간표 관리',
      icon: BookOpen,
      href: '/lectures',
      color: 'green'
    },
    {
      title: '학생 관리',
      description: '학생 등록, 정보 수정 및 성적 관리',
      icon: Users,
      href: '/students',
      color: 'purple'
    },
    {
      title: '과목 관리',
      description: '과목 개설 및 교육과정 관리',
      icon: FileText,
      href: '/subjects',
      color: 'orange'
    }
  ];

  const recentActivities = [
    {
      id: 1,
      type: 'notice',
      title: '새로운 공지사항이 등록되었습니다',
      description: '2025학년도 1학기 수강신청 안내',
      time: '2시간 전',
      icon: Bell
    },
    {
      id: 2,
      type: 'student',
      title: '신규 학생이 등록되었습니다',
      description: '김학생 (컴퓨터공학과)',
      time: '4시간 전',
      icon: Users
    },
    {
      id: 3,
      type: 'lecture',
      title: '강의가 개설되었습니다',
      description: '데이터베이스 시스템 (화요일 3교시)',
      time: '6시간 전',
      icon: BookOpen
    }
  ];

  return (
    <div className="main-page">
      {/* 헤더 */}
      <Header currentUser={currentUser} />

      <div className="main-content container">
        {/* 환영 메시지 */}
        <div className="welcome-section">
          <div className="welcome-content">
            <h1 className="welcome-title">
              안녕하세요, {currentUser.name}님! 👋
            </h1>
            <p className="welcome-subtitle">
              학점은행제 관리 시스템에 오신 것을 환영합니다.
            </p>
          </div>
          <div className="welcome-date">
            <Calendar className="welcome-date-icon" />
            <span>{new Date().toLocaleDateString('ko-KR', { 
              year: 'numeric', 
              month: 'long', 
              day: 'numeric',
              weekday: 'long'
            })}</span>
          </div>
        </div>

        {/* 통계 카드들 */}
        <div className="stats-grid">
          {stats.map((stat, index) => (
            <div key={index} className={`stat-card card stat-${stat.color}`}>
              <div className="stat-content">
                <div className="stat-info">
                  <h3 className="stat-title">{stat.title}</h3>
                  <p className="stat-value">{stat.value}</p>
                  <div className="stat-change">
                    <TrendingUp className="stat-change-icon" />
                    <span>{stat.change}</span>
                  </div>
                </div>
                <div className="stat-icon">
                  <stat.icon />
                </div>
              </div>
            </div>
          ))}
        </div>

        <div className="main-grid">
          {/* 빠른 액션 */}
          <div className="quick-actions-section">
            <div className="section-header">
              <h2 className="section-title">빠른 작업</h2>
              <p className="section-subtitle">자주 사용하는 기능들</p>
            </div>
            <div className="quick-actions-grid">
              {quickActions.map((action, index) => (
                <div key={index} className={`quick-action-card card action-${action.color}`}>
                  <div className="action-icon">
                    <action.icon />
                  </div>
                  <div className="action-content">
                    <h3 className="action-title">{action.title}</h3>
                    <p className="action-description">{action.description}</p>
                  </div>
                  <button className="action-button btn btn-primary">
                    바로가기
                  </button>
                </div>
              ))}
            </div>
          </div>

          {/* 최근 공지사항 */}
          <div className="recent-notices-section">
            <div className="section-header">
              <h2 className="section-title">최근 공지사항</h2>
              <button className="section-link btn btn-secondary">
                전체보기
              </button>
            </div>
            <div className="notices-list">
              {isLoading ? (
                <div className="notices-loading">
                  <div className="loading-spinner"></div>
                  <p>공지사항을 불러오는 중...</p>
                </div>
              ) : recentNotices.length === 0 ? (
                <div className="notices-empty">
                  <Bell className="empty-icon" />
                  <p>최근 공지사항이 없습니다.</p>
                </div>
              ) : (
                recentNotices.map((notice) => (
                  <div key={notice.notice_id} className="notice-item">
                    <div className="notice-content">
                      <div className="notice-header">
                        <h4 className="notice-title">{notice.notice_title}</h4>
                        <span className={getAudienceBadgeClass(notice.notice_target_audience)}>
                          {getAudienceText(notice.notice_target_audience)}
                        </span>
                      </div>
                      <p className="notice-preview">
                        {notice.notice_content.split('\n')[0]}
                      </p>
                      <div className="notice-meta">
                        <Clock className="meta-icon" />
                        <span>{formatDate(notice.created_at)}</span>
                      </div>
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>
        </div>

        {/* 최근 활동 */}
        <div className="recent-activities-section">
          <div className="section-header">
            <h2 className="section-title">최근 활동</h2>
            <p className="section-subtitle">시스템에서 일어난 최근 활동들</p>
          </div>
          <div className="activities-list card">
            {recentActivities.map((activity) => (
              <div key={activity.id} className="activity-item">
                <div className="activity-icon">
                  <activity.icon />
                </div>
                <div className="activity-content">
                  <h4 className="activity-title">{activity.title}</h4>
                  <p className="activity-description">{activity.description}</p>
                </div>
                <div className="activity-time">
                  <span>{activity.time}</span>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
// src/pages/NoticePage.tsx
import React, { useState, useEffect } from 'react';
import type { Notice, User, NoticeFormData, NoticeSearchFilters } from '../types/notice';
import { getNotices, createNotice, updateNotice } from '../apis/noticeApi';
import Header from '../components/Header';
import SearchFilter from '../components/SearchFilter';
import NoticeTable from '../components/NoticeTable';
import NoticeDetailModal from '../components/NoticeDetailModal';
import CreateNoticeModal from '../components/CreateNoticeModal';
import Pagination from '../components/Pagination';

const NoticePage: React.FC = () => {
  // 상태 관리
  const [notices, setNotices] = useState<Notice[]>([]);
  const [filteredNotices, setFilteredNotices] = useState<Notice[]>([]);
  const [selectedNotice, setSelectedNotice] = useState<Notice | null>(null);
  const [editNotice, setEditNotice] = useState<Notice | null>(null);
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // 검색 및 필터
  const [filters, setFilters] = useState<NoticeSearchFilters>({
    searchTerm: '',
    targetAudience: 'ALL'
  });

  // 페이지네이션
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);

  // 현재 사용자 (실제로는 인증 컨텍스트에서 가져와야 함)
  const [currentUser] = useState<User>({
    role: 'ADMIN',
    school_id: 1,
    name: '관리자',
    id: 'admin001'
  });

  // 공지사항 목록 로드
  useEffect(() => {
    const loadNotices = async () => {
      try {
        setIsLoading(true);
        const data = await getNotices();
        setNotices(data);
        setError(null);
      } catch (err) {
        setError('공지사항을 불러오는데 실패했습니다.');
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    loadNotices();
  }, []);

  // 검색 및 필터링
  useEffect(() => {
    let filtered = notices;

    // 검색어 필터
    if (filters.searchTerm) {
      filtered = filtered.filter(notice =>
        notice.notice_title.toLowerCase().includes(filters.searchTerm.toLowerCase()) ||
        notice.notice_content.toLowerCase().includes(filters.searchTerm.toLowerCase())
      );
    }

    // 대상 필터
    if (filters.targetAudience !== 'ALL') {
      filtered = filtered.filter(notice => notice.notice_target_audience === filters.targetAudience);
    }

    setFilteredNotices(filtered);
    setCurrentPage(1); // 필터 변경 시 첫 페이지로
  }, [notices, filters]);

  // 페이지네이션 계산
  const totalPages = Math.ceil(filteredNotices.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedNotices = filteredNotices.slice(startIndex, startIndex + itemsPerPage);

  // 모달 핸들러
  const openDetailModal = (notice: Notice) => {
    setSelectedNotice(notice);
    setIsDetailModalOpen(true);
  };

  const closeDetailModal = () => {
    setSelectedNotice(null);
    setIsDetailModalOpen(false);
  };

  const openCreateModal = () => {
    setEditNotice(null);
    setIsCreateModalOpen(true);
  };

  const openEditModal = (notice: Notice) => {
    setEditNotice(notice);
    setIsCreateModalOpen(true);
  };

  const closeCreateModal = () => {
    setEditNotice(null);
    setIsCreateModalOpen(false);
  };

  // 공지사항 생성/수정
  const handleNoticeSubmit = async (formData: NoticeFormData) => {
    try {
      if (editNotice) {
        // 수정
        const updatedNotice = await updateNotice(editNotice.notice_id, formData);
        setNotices(prev => prev.map(notice => 
          notice.notice_id === editNotice.notice_id ? updatedNotice : notice
        ));
      } else {
        // 새로 생성
        const newNotice = await createNotice(formData);
        setNotices(prev => [newNotice, ...prev]);
      }
      closeCreateModal();
    } catch (err) {
      console.error('공지사항 저장 실패:', err);
      alert('공지사항 저장에 실패했습니다.');
    }
  };

  // 로딩 상태
  if (isLoading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">공지사항을 불러오는 중...</p>
        </div>
      </div>
    );
  }

  // 에러 상태
  if (error) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="text-red-500 text-6xl mb-4">⚠️</div>
          <p className="text-red-600 text-lg">{error}</p>
          <button 
            onClick={() => window.location.reload()}
            className="mt-4 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
          >
            다시 시도
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* 헤더 */}
      <Header currentUser={currentUser} />

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* 검색 및 필터 */}
        <SearchFilter
          currentUser={currentUser}
          filters={filters}
          onFiltersChange={setFilters}
          onCreateClick={openCreateModal}
        />

        {/* 공지사항 테이블 */}
        <NoticeTable
          notices={paginatedNotices}
          currentUser={currentUser}
          onNoticeClick={openDetailModal}
          onEditClick={openEditModal}
        />

        {/* 페이지네이션 */}
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />
      </div>

      {/* 공지사항 상세보기 모달 */}
      <NoticeDetailModal
        notice={selectedNotice}
        currentUser={currentUser}
        isOpen={isDetailModalOpen}
        onClose={closeDetailModal}
        onEdit={openEditModal}
      />

      {/* 공지사항 작성/수정 모달 */}
      <CreateNoticeModal
        isOpen={isCreateModalOpen}
        editNotice={editNotice}
        onClose={closeCreateModal}
        onSubmit={handleNoticeSubmit}
      />
    </div>
  );
};

export default NoticePage;
// src/components/SearchFilter.tsx
import React from 'react';
import { Search, Plus, Filter } from 'lucide-react';
import type { User, NoticeSearchFilters } from '../types/notice';

interface SearchFilterProps {
  currentUser: User;
  filters: NoticeSearchFilters;
  onFiltersChange: (filters: NoticeSearchFilters) => void;
  onCreateClick: () => void;
}

const SearchFilter: React.FC<SearchFilterProps> = ({
  currentUser,
  filters,
  onFiltersChange,
  onCreateClick
}) => {
  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onFiltersChange({
      ...filters,
      searchTerm: e.target.value
    });
  };

  const handleAudienceChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    onFiltersChange({
      ...filters,
      targetAudience: e.target.value
    });
  };

  const canCreateNotice = currentUser.role === 'ADMIN' || currentUser.role === 'TEACHER';

  return (
    <div className="bg-white rounded-lg shadow-md p-6 mb-6">
      <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4">
        {/* 검색바 */}
        <div className="flex-1 max-w-md">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
            <input
              type="text"
              placeholder="공지사항 제목 또는 내용 검색..."
              value={filters.searchTerm}
              onChange={handleSearchChange}
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            />
          </div>
        </div>
        
        {/* 필터 및 버튼 */}
        <div className="flex items-center gap-4">
          {/* 대상 필터 */}
          <div className="flex items-center space-x-2">
            <Filter className="w-5 h-5 text-gray-400" />
            <select
              value={filters.targetAudience}
              onChange={handleAudienceChange}
              className="border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
              <option value="ALL">전체 대상</option>
              <option value="STUDENT">학생</option>
              <option value="TEACHER">교직원</option>
            </select>
          </div>
          
          {/* 새 공지사항 버튼 */}
          {canCreateNotice && (
            <button
              onClick={onCreateClick}
              className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg flex items-center space-x-2 transition-colors"
            >
              <Plus className="w-5 h-5" />
              <span>새 공지사항</span>
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default SearchFilter;
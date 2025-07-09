// src/components/Header.tsx
import React from 'react';
import type { User } from '../types/notice';

interface HeaderProps {
  currentUser: User;
}

const Header: React.FC<HeaderProps> = ({ currentUser }) => {
  const getRoleText = (role: string) => {
    switch (role) {
      case 'ADMIN': return '관리자';
      case 'TEACHER': return '교직원';
      case 'STUDENT': return '학생';
      default: return role;
    }
  };

  return (
    <header className="bg-white shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center py-6">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">공지사항 관리</h1>
            <p className="text-gray-600 mt-1">학점은행제 시스템</p>
          </div>
          <div className="flex items-center space-x-4">
            <div className="text-right">
              <span className="text-sm text-gray-500 block">
                {getRoleText(currentUser.role)}
              </span>
              <span className="text-sm font-medium text-gray-700">
                {currentUser.name}님 환영합니다
              </span>
            </div>
            <div className="h-10 w-10 bg-blue-500 rounded-full flex items-center justify-center">
              <span className="text-white text-sm font-medium">
                {currentUser.name[0]}
              </span>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
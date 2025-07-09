// src/components/Navbar.tsx
import React, { useState } from 'react';
import './Navbar.css'; // 스타일은 기존 style.css에서 관련된 것 분리

console.log('✅ Navbar 렌더링됨');

const Navbar: React.FC = () => {
  const [activeIndex, setActiveIndex] = useState<number | null>(null);

  const dropdownContents = [
    (
      <div className="dropdown-content-wrapper">
        <div className="dropdown-group">
          <h4>제도 소개</h4>
          <a href="#">제도 개요</a>
          <a href="#">운영 방식</a>
          <a href="#">도입 배경</a>
        </div>
      </div>
    ),
    (
      <div className="dropdown-content-wrapper">
        <div className="dropdown-group">
          <h4>공지 목록</h4>
          <a href="#">학사 공지</a>
          <a href="#">시스템 공지</a>
          <a href="#">이벤트 안내</a>
        </div>
      </div>
    )
  ];

  return (
    <header className={`navbar ${activeIndex !== null ? 'white-bg' : ''}`}>
  <div
    className="dropdown-wrapper" // ⬅️ 새로 추가
    onMouseEnter={() => {}}
    onMouseLeave={() => setActiveIndex(null)} // 전체 벗어났을 때만 닫힘
  >
    <div className="navbar-container">
      <a href="/" className="navbar-logo">
        <img src="/로고.png" alt="로고" className="logo-img" />
        <span className="logo-title">고교학점제 수강신청</span>
      </a>
      <nav className="navbar-menu">
        {['고교학점제 소개', '공지사항'].map((title, index) => (
          <div
            key={index}
            className="dropdown"
            onMouseEnter={() => setActiveIndex(index)} // 마우스 올리면 열림
          >
            <span className="menu-item">{title}</span>
          </div>
        ))}
      </nav>
    </div>

    {activeIndex !== null && (
      <div className="dropdown-area active">
        {dropdownContents[activeIndex]}
      </div>
    )}
  </div>
</header>
  );
};

export default Navbar;

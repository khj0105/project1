// src/components/layout/MainLayout.tsx
import React from 'react';
import { Outlet, NavLink } from 'react-router-dom';

// NavLink의 active 스타일을 위한 CSS를 추가할 수 있습니다.
// 예를 들어, App.css 파일에 아래 내용을 추가하세요.
// .temp-nav a.active {
//   color: red;
//   font-weight: bold;
// }

const MainLayout: React.FC = () => {
  return (
    <div className="App">
      <nav className="temp-nav">
        {/*
          <button> 대신 <NavLink>를 사용합니다.
          'to' 속성으로 이동할 경로를 지정합니다.
          NavLink는 현재 경로와 to 속성이 일치하면 자동으로 'active' 클래스를 추가해줍니다.
        */}
        <NavLink to="/main">
          메인
        </NavLink>
        <NavLink to="/notices">
          공지사항
        </NavLink>
      </nav>
      
      {/*
        <Outlet />은 자식 라우트(여기서는 MainPage, NoticePage 등)가 렌더링될 위치를 지정합니다.
      */}
      <main>
        <Outlet />
      </main>
    </div>
  );
};

export default MainLayout;
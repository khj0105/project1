import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import '../App.css';
import LoginPage from '../pages/sign-in/LoginPage';
import RegisterSelectPage from '../pages/sign-up/RegisterSelectPage'; 
import RegisterStudentPage from '../pages/sign-up/RegisterStudentPage';
import RegisterTeacherPage from '../pages/sign-up/RegisterTeacherPage';
import FindIdPage from '../pages/sign-in/FindIdPage';
import FindPasswordPage from '../pages/sign-in/FindPasswordPage';
import LectureManagementPage from '../pages/subject-lecture/LectureManagementPage';
import LectureManagementDetailPage from '../pages/subject-lecture/LectureManagementDetailPage';
import MainLayout from '../layouts/MainLayout';
import MainPage from '../pages/MainPage';
import NoticePage from '../pages/NoticePage';
import UnifiedSearchPage from '../pages/search/UnifiedSearch';

const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register/select" element={<RegisterSelectPage />} /> {}
        <Route path="/register/student" element={<RegisterStudentPage />} />
        <Route path="/register/teacher" element={<RegisterTeacherPage />} /> 
        <Route path="/find-id" element={<FindIdPage />} />
        <Route path="/find-password" element={<FindPasswordPage />} />
        <Route path="/lecture-management" element={<LectureManagementPage/>} />
        <Route path="/lecture-management/:id" element={<LectureManagementDetailPage/>} />
        <Route element={<MainLayout />}>
          <Route path="/main" element={<MainPage />} />
          <Route path="/notices" element={<NoticePage />} />
          <Route path="/search" element={<UnifiedSearchPage />} />

        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;

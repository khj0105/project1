import React from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from '../../components/Layout'; 
import './RegisterSelectPage.css';

const RegisterSelectPage: React.FC = () => {
  const navigate = useNavigate();

  return (
    <Layout>
      <main className="register-page">
        <div className="register-box">
          <h1 className="register-title">회원가입</h1>
          <div className="option-container">
            <div className="option-card" onClick={() => navigate('/register/student')}>
              <img src="/학생.png" alt="학생" />
              <p>학생</p>
            </div>
            <div className="option-card" onClick={() => navigate('/register/teacher')}>
              <img src="/교사.png" alt="교사" />
              <p>교사 / 교직원</p>
            </div>
          </div>
        </div>
      </main>
    </Layout>
  );
};

export default RegisterSelectPage;

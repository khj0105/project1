// src/pages/FindPasswordPage.tsx
import React, { useState } from 'react';
import Layout from '../../components/Layout';
import './FindPasswordPage.css';

const FindPasswordPage: React.FC = () => {
  const [userId, setUserId] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // TODO: 비밀번호 찾기 로직 연결
    alert(`입력한 아이디: ${userId}`);
  };

  return (
    <Layout>
      <div className="pw-find-container">
        <h2>비밀번호 찾기</h2>
        <p>비밀번호를 찾고자하는 아이디를 입력해주세요.</p>

        <form onSubmit={handleSubmit} className="pw-form">
          <input
            type="text"
            placeholder="아이디"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
          />
          <button type="submit">다음</button>
        </form>

        <p className="find-id-link">
          아이디가 기억나지 않는다면?{' '}
          <span onClick={() => window.location.href = '/find-id'}>아이디 찾기</span>
        </p>
      </div>
    </Layout>
  );
};

export default FindPasswordPage;

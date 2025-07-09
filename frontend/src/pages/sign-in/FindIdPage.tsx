import React, { useState } from 'react';
import Layout from '../../components/Layout';
import './FindIdPage.css';

const FindIdPage: React.FC = () => {
  const [method, setMethod] = useState<'phone' | 'email'>('phone');
  const [name, setName] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [authCode, setAuthCode] = useState('');

  return (
    <Layout>
    <div className="find-id-container">
      <h2>아이디 찾기</h2>
      <p className="find-id-sub">아이디 찾는 방법을 선택해 주세요.</p>

      <div className="find-id-methods">
        <label>
          <input
            type="radio"
            checked={method === 'phone'}
            onChange={() => setMethod('phone')}
          />
          회원정보에 등록한 <strong>휴대전화로 인증</strong>
        </label>
        <label>
          <input
            type="radio"
            checked={method === 'email'}
            onChange={() => setMethod('email')}
          />
          본인확인 <strong>이메일로 인증</strong>
        </label>
      </div>

      <div className="find-id-form">
        <input
          type="text"
          placeholder="이름"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />


        {method === 'phone' && (
          <>
            <input
              type="tel"
              placeholder="휴대전화번호 (예: 01012345678)"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
            />
            <div className="auth-row">
              <input
                type="text"
                placeholder="인증번호 6자리 입력"
                value={authCode}
                onChange={(e) => setAuthCode(e.target.value)}
              />
              <button className="auth-btn">인증번호 받기</button>
            </div>
          </>
        )}

        {method === 'email' && (
          <>
            <input
              type="email"
              placeholder="이메일 주소"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <div className="auth-row">
              <input
                type="text"
                placeholder="인증번호 6자리 입력"
                value={authCode}
                onChange={(e) => setAuthCode(e.target.value)}
              />
              <button className="auth-btn">인증번호 받기</button>
            </div>
          </>
        )}

        <button className="find-id-submit">아이디 찾기</button>
      </div>
    </div>
    </Layout>
  );
};

export default FindIdPage;

import React from 'react';
import './RegisterForm.css';

interface RegisterFormProps {
  role: 'STUDENT' | 'TEACHER';
}

const RegisterForm: React.FC<RegisterFormProps> = ({ role }) => {
  return (
    <div className="register-form-container">
      <h2>{role === 'STUDENT' ? '학생' : '교사 / 교직원'} 회원가입</h2>
      <form className="register-form">
        <input type="text" placeholder="이름" />
        <input type="text" placeholder="아이디" />
        <input type="password" placeholder="비밀번호" />
        <input type="email" placeholder="이메일" />
        <input
          type="text"
          placeholder="생년월일 8자리 (예: 20050915)"
          maxLength={8}
        />
        <input type="tel" placeholder="전화번호" />
        <select>
          <option value="">성별 선택</option>
          <option value="male">남성</option>
          <option value="female">여성</option>
        </select>
        <button type="submit">인증 요청</button>
      </form>
    </div>
  );
};

export default RegisterForm;

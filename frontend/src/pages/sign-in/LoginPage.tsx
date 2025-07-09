import React from 'react';
import Layout from '../../components/Layout';
import LoginForm from '../../components/LoginForm';

const LoginPage: React.FC = () => {
  console.log('✅ LoginPage 렌더링됨');

  return (
    <Layout>
      <main className="login-area">
        <LoginForm />
      </main>
    </Layout>
  );
};

export default LoginPage;
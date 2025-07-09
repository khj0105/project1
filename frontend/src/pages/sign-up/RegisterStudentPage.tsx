import React from 'react';
import RegisterForm from '../../components/RegisterForm';
import Layout from '../../components/Layout';

const RegisterStudentPage: React.FC = () => {
  return (
    <Layout>
      <RegisterForm role="STUDENT" />
    </Layout>
  );
};

export default RegisterStudentPage;

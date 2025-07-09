import React from 'react';

const ListHeader = () => {
  return (
    <div className="list-header">
      <div className="header-item" style={{ flexBasis: '25%' }}>과목명</div>
      <div className="header-item" style={{ flexBasis: '15%' }}>신청자</div>
      <div className="header-item" style={{ flexBasis: '15%' }}>신청일</div>
      <div className="header-item" style={{ flexBasis: '10%' }}>학년</div>
      <div className="header-item" style={{ flexBasis: '10%' }}>학기</div>
      <div className="header-item" style={{ flexBasis: '10%' }}>계열</div>
      <div className="header-item" style={{ flexBasis: '15%', textAlign: 'center' }}>상태</div>
    </div>
  );
};

export default ListHeader;
import React from 'react';

const FilterBar = () => {
  return (
    <div className="filter-bar-container">
      <select name="track">
        <option value="">계열 전체</option>
        <option value="arts">문과</option>
        <option value="science">이과</option>
      </select>
      <select name="grade">
        <option value="">학년 전체</option>
        <option value="1">1학년</option>
        <option value="2">2학년</option>
        <option value="3">3학년</option>
      </select>
      <select name="semester">
        <option value="">학기 전체</option>
        <option value="1">1학기</option>
        <option value="2">2학기</option>
      </select>
      <input type="text" placeholder="과목 이름으로 검색" />
      <button className="search-button">검색</button>
    </div>
  );
};

export default FilterBar;
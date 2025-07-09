// src/apis/noticeApi.ts
import type { Notice, NoticeFormData } from '../types/notice';

// const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

// 더미 데이터 (실제 API 연동 전까지 사용)
const dummyNotices: Notice[] = [
  {
    notice_id: 1,
    school_id: 1,
    notice_title: "2025학년도 1학기 수강신청 안내",
    notice_content: "2025학년도 1학기 수강신청이 다음과 같이 진행됩니다.\n\n- 신청기간: 2025년 2월 10일 ~ 2월 20일\n- 신청방법: 학교 홈페이지 로그인 후 수강신청 메뉴\n- 문의사항: 학사과 (02-1234-5678)\n\n자세한 내용은 첨부파일을 참고하시기 바랍니다.",
    notice_target_audience: 'STUDENT',
    notice_start_date: '2025-02-01',
    notice_end_date: '2025-02-28',
    created_at: '2025-01-15T09:00:00Z',
    updated_at: '2025-01-15T09:00:00Z'
  },
  {
    notice_id: 2,
    school_id: 1,
    notice_title: "교직원 워크샵 개최 안내",
    notice_content: "교직원 대상 워크샵을 다음과 같이 개최합니다.\n\n- 일시: 2025년 1월 30일 (목) 14:00\n- 장소: 본관 대회의실\n- 주제: 디지털 교육 플랫폼 활용법\n\n필수 참석 부탁드립니다.",
    notice_target_audience: 'TEACHER',
    notice_start_date: '2025-01-20',
    notice_end_date: '2025-01-30',
    created_at: '2025-01-20T10:30:00Z',
    updated_at: '2025-01-20T10:30:00Z'
  },
  {
    notice_id: 3,
    school_id: 1,
    notice_title: "학교 시설 점검으로 인한 임시 휴관 안내",
    notice_content: "정기 시설 점검으로 인해 다음 기간 동안 일부 시설이 휴관됩니다.\n\n- 휴관기간: 2025년 2월 5일 ~ 2월 7일 (3일간)\n- 대상시설: 도서관, 체육관, 학생회관\n- 문의: 시설관리과 (02-1234-5679)\n\n이용에 불편을 드려 죄송합니다.",
    notice_target_audience: 'ALL',
    notice_start_date: '2025-01-25',
    notice_end_date: '2025-02-10',
    created_at: '2025-01-25T11:00:00Z',
    updated_at: '2025-01-25T11:00:00Z'
  }
];

// 공지사항 목록 조회
export const getNotices = async (): Promise<Notice[]> => {
  try {
    // 실제 API 호출
    // const response = await fetch(`${API_BASE_URL}/notices?schoolId=${schoolId}`);
    // const data = await response.json();
    // return data;
    
    // 더미 데이터 반환 (개발용)
    return new Promise((resolve) => {
      setTimeout(() => resolve(dummyNotices), 500);
    });
  } catch (error) {
    console.error('Failed to fetch notices:', error);
    throw error;
  }
};

// 공지사항 상세 조회
export const getNoticeById = async (noticeId: number): Promise<Notice> => {
  try {
    // 실제 API 호출
    // const response = await fetch(`${API_BASE_URL}/notices/${noticeId}`);
    // const data = await response.json();
    // return data;
    
    // 더미 데이터 반환 (개발용)
    const notice = dummyNotices.find(n => n.notice_id === noticeId);
    if (!notice) throw new Error('Notice not found');
    return Promise.resolve(notice);
  } catch (error) {
    console.error('Failed to fetch notice:', error);
    throw error;
  }
};

// 새 공지사항 생성
export const createNotice = async (noticeData: NoticeFormData): Promise<Notice> => {
  try {
    // 실제 API 호출
    // const response = await fetch(`${API_BASE_URL}/notices`, {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //     'Authorization': `Bearer ${getAuthToken()}`
    //   },
    //   body: JSON.stringify(noticeData)
    // });
    // const data = await response.json();
    // return data;
    
    // 더미 데이터 반환 (개발용)
    const newNotice: Notice = {
      notice_id: Date.now(),
      school_id: 1,
      ...noticeData,
      created_at: new Date().toISOString(),
      updated_at: new Date().toISOString()
    };
    
    return Promise.resolve(newNotice);
  } catch (error) {
    console.error('Failed to create notice:', error);
    throw error;
  }
};

// 공지사항 수정
export const updateNotice = async (noticeId: number, noticeData: Partial<NoticeFormData>): Promise<Notice> => {
  try {
    // 실제 API 호출
    // const response = await fetch(`${API_BASE_URL}/notices/${noticeId}`, {
    //   method: 'PUT',
    //   headers: {
    //     'Content-Type': 'application/json',
    //     'Authorization': `Bearer ${getAuthToken()}`
    //   },
    //   body: JSON.stringify(noticeData)
    // });
    // const data = await response.json();
    // return data;
    
    // 더미 데이터 반환 (개발용)
    const existingNotice = dummyNotices.find(n => n.notice_id === noticeId);
    if (!existingNotice) throw new Error('Notice not found');
    
    const updatedNotice: Notice = {
      ...existingNotice,
      ...noticeData,
      updated_at: new Date().toISOString()
    };
    
    return Promise.resolve(updatedNotice);
  } catch (error) {
    console.error('Failed to update notice:', error);
    throw error;
  }
};

// 공지사항 삭제
export const deleteNotice = async (noticeId: number): Promise<void> => {
  try {
    // 실제 API 호출
    // await fetch(`${API_BASE_URL}/notices/${noticeId}`, {
    //   method: 'DELETE',
    //   headers: {
    //     'Authorization': `Bearer ${getAuthToken()}`
    //   }
    // });
    
    // 더미 데이터 처리 (개발용)
    console.log(`Notice ${noticeId} deleted`);
    return Promise.resolve();
  } catch (error) {
    console.error('Failed to delete notice:', error);
    throw error;
  }
};
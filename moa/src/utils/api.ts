// src/utils/api.ts
import axios from 'axios';

const api = axios.create({
  baseURL: 'https://k11a602.p.ssafy.io/api/',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터
api.interceptors.request.use(
  (config) => {
    // 토큰을 로컬 저장소에서 가져와 헤더에 추가
    const token = 'your-auth-token'; // 추후에 실제 토큰 값으로 변경
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

// 응답 인터셉터
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // 401 에러 처리
    if (error.response && error.response.status === 401) {
      console.error('Unauthorized, please log in again');
    }
    return Promise.reject(error);
  },
);

export default api;

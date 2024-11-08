// src/stores/authStore.ts
import {create} from 'zustand';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {persist, createJSONStorage} from 'zustand/middleware';

interface AuthState {
  isAuthenticated: boolean;
  jwtToken: string | null;
  setAuthenticated: (authStatus: boolean, token?: string) => void;
  checkAuthStatus: () => Promise<void>;
  logout: () => Promise<void>;
}

export const useAuthStore = create<AuthState>()(
  persist(
    // persist로 상태 영구히 관리
    (set) => ({
      isAuthenticated: false,
      jwtToken: null,

      // 인증 상태를 설정하는 함수
      setAuthenticated: async (authStatus, token) => {
        if (authStatus && token) {
          await AsyncStorage.setItem('jwtToken', token);
          set({isAuthenticated: true, jwtToken: token});
        } else {
          set({isAuthenticated: false, jwtToken: null});
        }
      },

      // AsyncStorage에서 토큰을 가져와 인증 상태 확인
      checkAuthStatus: async () => {
        const token = await AsyncStorage.getItem('jwtToken');
        set({isAuthenticated: !!token, jwtToken: token});
      },

      // 로그아웃 및 AsyncStorage에서 토큰 제거
      logout: async () => {
        await AsyncStorage.removeItem('jwtToken');
        set({isAuthenticated: false, jwtToken: null});
      },
    }),
    {
      name: 'kakao-auth-storage', // AsyncStorage에 저장할 키 이름
      //createJSONStorage -> AsyncStorage를 Zustand와 호환되도록 자동으로 JSON으로 직렬화/역직렬화
      storage: createJSONStorage(() => AsyncStorage),
    },
  ),
);

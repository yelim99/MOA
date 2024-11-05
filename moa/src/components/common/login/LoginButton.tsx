// src/components/common/login/LoginButton.tsx
import React from 'react';
import {Button, Alert} from 'react-native';
import {login as kakaoLogin} from '@react-native-seoul/kakao-login';

type LoginButtonProps = {
  onLoginSuccess: (kakaoToken: string) => void;
};

const LoginButton: React.FC<LoginButtonProps> = ({onLoginSuccess}) => {
  const handleKakaoLogin = async () => {
    try {
      const result = await kakaoLogin();
      const kakaoToken = result.accessToken;

      if (kakaoToken) {
        onLoginSuccess(kakaoToken);
      } else {
        throw new Error('카카오 토큰 발급 못받음!');
      }
    } catch (error) {
      console.error('카카오 로그인 실패', error);
      Alert.alert('로그인 실패', '다시 시도해주세용');
    }
  };

  return <Button title="Login with Kakao" onPress={handleKakaoLogin} />;
};

export default LoginButton;

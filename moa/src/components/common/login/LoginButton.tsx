// src/components/common/login/LoginButton.tsx
import React, {useState, useEffect} from 'react';
import {Button, Alert, View} from 'react-native';
import {login} from '@react-native-seoul/kakao-login';
import styled from 'styled-components/native';

type LoginButtonProps = {
  onLoginSuccess: (kakaoToken: string) => void;
};

const KakaoLoginButton = styled.Button`
  width: 200px;
  height: 50px;
`;

// const LoginButton: React.FC<LoginButtonProps> = ({onLoginSuccess}) => {
const LoginButton = () => {
  // const handleKakaoLogin = async () => {
  //   try {
  //     const result = await kakaoLogin();
  //     const kakaoToken = result.accessToken;

  //     if (kakaoToken) {
  //       onLoginSuccess(kakaoToken);
  //     } else {
  //       throw new Error('카카오 토큰 발급 못받음!');
  //     }
  //   } catch (error) {
  //     console.error('카카오 로그인 실패', error);
  //     Alert.alert('로그인 실패', '다시 시도해주세용');
  //   }
  // };
  const [result, setResult] = useState<string | null>(null);
  const signInWithKakao = async () => {
    try {
      const token = await login();
      const kakaoToken = token.accessToken;
      setResult(`Kakao Login Success: ${JSON.stringify(token)}`);
      if (token) {
        console.log('토큰: ', result);
        console.log('access토큰: ', token.accessToken);
        // onLoginSuccess(kakaoToken);
      } else {
        throw new Error('카카오 토큰 발급 못받음!');
      }
    } catch (error) {
      console.error('Login Failed', error);
      Alert.alert('Login Failed', 'Please try again.');
    }
  };

  return (
    <View>
      <Button title="Login with Kakao" onPress={signInWithKakao} />;
    </View>
  );
};

export default LoginButton;

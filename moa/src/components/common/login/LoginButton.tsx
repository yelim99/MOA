// src/components/common/login/LoginButton.tsx
import React, {useState, useEffect} from 'react';
import {Button, Alert, View, TouchableOpacity, Text} from 'react-native';
import {login, logout} from '@react-native-seoul/kakao-login';
import styled from 'styled-components/native';
import {TextButton} from '../button/TextButton';
import Ionicons from 'react-native-vector-icons/Ionicons';
type LoginButtonProps = {
  onLoginSuccess: (kakaoToken: string) => void;
};

const KakaoLoginButton = styled.Button`
  width: 200px;
  height: 50px;
  border-radius: 15px;
  background-color: #000000;
`;
const ButtonContainer = styled(TouchableOpacity)`
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 60px;
  border-radius: 15px;
  background-color: #fee500;
  padding: 10px;
`;

const ButtonText = styled(Text)`
  color: #3c1e1e;
  font-size: 16px;
  margin-left: 8px;
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
        console.log('refresh토큰: ', token.refreshToken);
        // onLoginSuccess(kakaoToken);
      } else {
        throw new Error('카카오 토큰 발급 못받음!');
      }
    } catch (error) {
      console.error('Login Failed', error);
      Alert.alert('Login Failed', 'Please try again.');
    }
  };

  const signOutWithKakao = async () => {
    try {
      const message = await logout();
      setResult(`Kakao Logout: ${message}`);
      console.log('로그아웃 결과: ', result, '토큰은? ', setResult);
    } catch (error) {
      console.error('Logout Failed', error);
      Alert.alert('Logout Failed', 'Please try again.');
    }
  };
  return (
    <View>
      {/* <KakaoLoginButton title="카카오 로그인mkm" onPress={signInWithKakao} />; */}

      <ButtonContainer onPress={signInWithKakao}>
        <Ionicons name="chatbubble-sharp" size={24} color="#3c1e1e" />
        <ButtonText>카카오 로그인</ButtonText>
      </ButtonContainer>

      <ButtonContainer onPress={signOutWithKakao}>
        <Ionicons name="chatbubble-sharp" size={24} color="#3c1e1e" />
        <ButtonText>카카오 로그아웃</ButtonText>
      </ButtonContainer>
    </View>
  );
};

export default LoginButton;

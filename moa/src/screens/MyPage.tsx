import {View, Text} from 'react-native';
import React from 'react';
import ScreenContainer from '../components/common/ScreenContainer';
import styled, {css, useTheme, DefaultTheme} from 'styled-components/native';
import {IconButton} from '../components/common/button/IconButton';
import StyleSheet from 'styled-components/dist/sheet';
import {TextButton} from '../components/common/button/TextButton';
import MyInfo from '../components/mypage/MyInfo';
import FaceImage from '../components/mypage/FaceImage';

interface TextProps {
  variant: 'title' | 'subtitle' | 'body';
  color?: string;
}

const Texts = styled.Text<TextProps>`
  ${({variant, theme, color}) => {
    const textColor = color || theme.colors.black;
    switch (variant) {
      case 'title':
        return css`
          font-size: 22px;
          font-family: ${theme.fontFamily.SCDream8};
          color: ${textColor};
        `;
      case 'subtitle':
        return css`
          font-size: 16px;
          font-family: ${theme.fontFamily.SCDream6};
          color: ${textColor};
        `;
      case 'body':
      default:
        return css`
          font-size: 13px;
          font-family: ${theme.fontFamily.SCDream4};
          color: ${textColor};
        `;
    }
  }}
`;

const Face = styled.View`
  width: 350px;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
`;
const MyPage = (props: {theme: any}) => {
  const DummyData = [
    {
      name: '에브리데이',
      group: 3, // 내 그룹
      totalDownloads: 1500, // 누적 다운로드 수
    },
    {
      name: '이영희',
      group: 5, // 내 그룹
      totalDownloads: 2300, // 누적 다운로드 수
    },
    {
      name: '김철수',
      group: 1, // 내 그룹
      totalDownloads: 500, // 누적 다운로드 수
    },
  ];
  const theme = useTheme();
  return (
    <ScreenContainer>
      <View>
        <Texts variant="title">마이페이지</Texts>
        <MyInfo></MyInfo>
        <Face>
          <Texts variant="subtitle">얼굴 등록</Texts>
          <FaceImage></FaceImage>
        </Face>
      </View>
    </ScreenContainer>
  );
};

export default MyPage;

import {View} from 'react-native';
import React from 'react';
import ScreenContainer from '../components/common/ScreenContainer';
import styled, {css} from 'styled-components/native';
import MyInfo from '../components/mypage/MyInfo';
import FaceImage from '../components/mypage/FaceImage';
import Partition from '../components/common/Partition';

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
          margin: 0 0 10px 0;
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

// 중간에 선 넣기 나중에 시도
const Divider = styled.View`
  border: 1px solid ${(props) => props.theme.colors.lightgray};
  width: 100%;
`;

const Face = styled.View`
  width: 100%;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
`;

const MyPage = () => {
  return (
    <ScreenContainer>
      <View>
        <Texts variant="title">마이페이지</Texts>
        <MyInfo />
      </View>
      <Partition />
      <Face>
        <FaceImage />
      </Face>
    </ScreenContainer>
  );
};

export default MyPage;

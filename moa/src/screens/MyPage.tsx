import {View, Text} from 'react-native';
import React from 'react';
import ScreenContainer from '../components/common/ScreenContainer';
import styled, {css, useTheme, DefaultTheme} from 'styled-components/native';
import {IconButton} from '../components/common/button/IconButton';
import StyleSheet from 'styled-components/dist/sheet';
interface TextProps {
  variant: 'title' | 'subtitle' | 'body';
}

const MyInfo = styled.View`
  border-radius: 15px;
  elevation: 2;
  height: 100px;
  background-color: ${(props) => props.theme.colors.white};
  margin: 10px 0;
  padding: 30px;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
`;

const TextInfo = styled.View`
  flex-direction: column;
  width: 200px;
  color: ${(props) => props.theme.colors.black};
`;

const Texts = styled.Text<TextProps>`
  ${({variant, theme}) => {
    switch (variant) {
      case 'title':
        return css`
          font-size: 22px;
          font-family: ${theme.fontFamily.SCDream8};
        `;
      case 'subtitle':
        return css`
          font-size: 16px;
          font-family: ${theme.fontFamily.SCDream6};
        `;
      case 'body':
      default:
        return css`
          font-size: 13px;
          font-family: ${theme.fontFamily.SCDream4};
        `;
    }
  }}
`;
const IconButtonStyled = styled.View`
  position: absolute;
  top: 8px;
  right: 8px;
`;

const MyProfile = styled.Image`
  border-radius: 50px;
  border: 1px solid ${(props) => props.theme.colors.lightgray};
  width: 70px;
  height: 70px;
  background-color: ${(props) => props.theme.colors.maindarkorange};
`;

const MyPage = (props: {theme: any}) => {
  const DummyData = [
    {
      name: '홍길동',
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
        <MyInfo>
          <TextInfo>
            <Texts variant="subtitle">{DummyData[0].name}님 안녕하세요!</Texts>
            <Texts variant="body">내 그룹 | {DummyData[0].group} 개</Texts>
            <Texts variant="body">
              누적 다운로드 사진 수 | {DummyData[0].totalDownloads} 장
            </Texts>
          </TextInfo>
          <MyProfile src="/"></MyProfile>
          <IconButtonStyled>
            <IconButton
              backcolor="white"
              iconName="edit"
              iconSet="Material"
              onPress={() => console.log('편집 아이콘 버튼 눌렀음')}
            ></IconButton>
          </IconButtonStyled>
        </MyInfo>
      </View>
    </ScreenContainer>
  );
};

export default MyPage;

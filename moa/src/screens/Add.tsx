import React from 'react';
import ScreenContainer from '../components/common/ScreenContainer';
import styled from 'styled-components/native';
import {TextButton} from '../components/common/button/TextButton';
import {useNavigation} from '@react-navigation/native';
import {HomeScreenNavigationProp} from '../types/screen';

const ContentContainer = styled.View`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  padding-bottom: 120px;
`;

const ButtonContainer = styled.View`
  margin: auto;
  height: 120px;
  display: flex;
  justify-content: space-between;
`;

const Add = () => {
  const navigation = useNavigation<HomeScreenNavigationProp>();

  return (
    <ScreenContainer>
      <ContentContainer>
        <ButtonContainer>
          <TextButton
            backcolor="maindarkorange"
            size="large"
            text="나의 그룹 만들기"
            onPress={() => navigation.navigate('GroupAdd')}
          />
          <TextButton
            backcolor="mainlightyellow"
            size="large"
            text="나의 순간 만들기"
            onPress={() => navigation.navigate('MomentAdd')}
          />
        </ButtonContainer>
      </ContentContainer>
    </ScreenContainer>
  );
};

export default Add;

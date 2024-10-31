import {View, Text} from 'react-native';
import React from 'react';
import styled from 'styled-components/native';
import Icon from 'react-native-vector-icons/Ionicons';
import {theme} from '../../../styles/theme';

const Container = styled.View`
  height: 70px;
  background-color: ${({theme}) => theme.colors.white};
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 10%;
`;

const ButtonContainer = styled.View`
  display: flex;
  align-items: center;
`;

const Logo = styled.Image`
  width: 50px;
  height: 30px;
`;

const AppHeader = ({navigation}) => {
  return (
    <Container>
      <ButtonContainer>
        <Logo source={require('../../../assets/images/logo.png')} />
      </ButtonContainer>
      <ButtonContainer>
        <Icon
          name="notifications"
          size={30}
          color={theme.colors.maindarkorange}
        />
      </ButtonContainer>
    </Container>
  );
};

export default AppHeader;

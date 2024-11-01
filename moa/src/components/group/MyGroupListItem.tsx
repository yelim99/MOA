import {View, Text} from 'react-native';
import React from 'react';
import styled from 'styled-components/native';

const Container = styled.View`
  width: 45%;
  aspect-ratio: 1;
  background-color: ${({theme}) => theme.colors.lightred};
  padding: 10px;
  border-radius: 20px;
`;

const TitleText = styled.Text`
  font-family: SCDream6;
  font-size: 22px;
`;

const ContentText = styled.Text`
  font-family: SCDream4;
`;
const ContentColor = styled(ContentText)`
  color: ${({theme}) => theme.colors.darkred};
`;

const MyGroupListItem = () => {
  return (
    <Container>
      <TitleText>싸피7반</TitleText>
      <ContentColor>사진 527장</ContentColor>
    </Container>
  );
};

export default MyGroupListItem;

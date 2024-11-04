import {TouchableOpacity} from 'react-native';
import React from 'react';
import styled, {useTheme} from 'styled-components/native';
import Icon from 'react-native-vector-icons/Ionicons';
import {useNavigation} from '@react-navigation/native';
import {StackHeaderNavigationProp} from '../../../types/screen';

const Container = styled.View`
  height: 70px;
  background-color: ${({theme}) => theme.colors.white};
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0 8%;
`;

const StyledIcon = styled(Icon)`
  margin-right: auto;
`;

const TitleContainer = styled.View`
  flex: 1;
  align-items: center;
`;

const Title = styled.Text`
  font-family: 'SCDream5';
  font-size: 20px;
`;

interface StackHeaderProps {
  title: string;
}

export default function StackHeader({title}: StackHeaderProps) {
  const navigation = useNavigation<StackHeaderNavigationProp>();
  const theme = useTheme();

  return (
    <Container>
      <TouchableOpacity onPress={() => navigation.goBack()}>
        <StyledIcon
          name="chevron-back"
          size={30}
          color={theme.colors.maindarkorange}
        />
      </TouchableOpacity>
      <TitleContainer>
        <Title>{title}</Title>
      </TitleContainer>
    </Container>
  );
}

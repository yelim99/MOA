import {View, Text, TouchableOpacity} from 'react-native';
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
  justify-content: space-between;
  padding: 0 10%;
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
        <Icon
          name="chevron-back"
          size={30}
          color={theme.colors.maindarkorange}
        />
      </TouchableOpacity>
      <Title>{title}</Title>
      <TouchableOpacity onPress={() => navigation.navigate('Notification')}>
        {title === '알림' ? null : (
          <Icon
            name="notifications"
            size={25}
            color={theme.colors.maindarkorange}
          />
        )}
      </TouchableOpacity>
    </Container>
  );
}

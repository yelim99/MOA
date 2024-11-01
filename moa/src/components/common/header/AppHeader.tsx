import {TouchableOpacity} from 'react-native';
import React from 'react';
import styled, {useTheme} from 'styled-components/native';
import Icon from 'react-native-vector-icons/Ionicons';
import {AppHeaderNavigationProp} from '../../../types/screen';

const Container = styled.View`
  height: 70px;
  background-color: ${({theme}) => theme.colors.white};
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 10%;
`;

const Logo = styled.Image`
  width: 50px;
  height: 30px;
`;

interface AppHeaderProps {
  navigation: AppHeaderNavigationProp;
}

const AppHeader: React.FC<AppHeaderProps> = ({navigation}) => {
  const theme = useTheme();

  return (
    <Container>
      <TouchableOpacity onPress={() => navigation.navigate('Home')}>
        <Logo source={require('../../../assets/images/logo.png')} />
      </TouchableOpacity>
      <TouchableOpacity onPress={() => navigation.navigate('Notification')}>
        <Icon
          name="notifications"
          size={25}
          color={theme.colors.maindarkorange}
        />
      </TouchableOpacity>
    </Container>
  );
};

export default AppHeader;

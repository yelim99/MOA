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
  padding: 0 8%;
`;

const Logo = styled.Image`
  width: 50px;
  height: 30px;
`;

const ButtonLine = styled.View`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const IconButton = styled.TouchableOpacity`
  margin-left: 10px;
`;

interface AppHeaderProps {
  navigation: AppHeaderNavigationProp;
  isHome?: boolean;
}

const AppHeader = ({navigation, isHome = false}: AppHeaderProps) => {
  const theme = useTheme();

  return (
    <Container>
      <TouchableOpacity onPress={() => navigation.navigate('Home')}>
        <Logo source={require('../../../assets/images/logo.png')} />
      </TouchableOpacity>
      <ButtonLine>
        {isHome && (
          <IconButton onPress={() => navigation.navigate('Add')}>
            <Icon
              name="add-circle-outline"
              size={25}
              color={theme.colors.maindarkorange}
            />
          </IconButton>
        )}
        <IconButton onPress={() => navigation.navigate('Notification')}>
          <Icon
            name="notifications"
            size={25}
            color={theme.colors.maindarkorange}
          />
        </IconButton>
      </ButtonLine>
    </Container>
  );
};

export default AppHeader;

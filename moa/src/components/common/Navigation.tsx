import React from 'react';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import FeatherIcon from 'react-native-vector-icons/Feather';
import styled, {useTheme} from 'styled-components/native';
import {BottomTabBarProps} from '@react-navigation/bottom-tabs';
import {Shadow} from 'react-native-shadow-2';

const Container = styled.View`
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
`;

const BoxContainer = styled.View`
  height: 85px;
  width: 100%;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 10%;
  background-color: ${({theme}) => theme.colors.white};
  margin-top: 10px;
`;

interface NavButtonProps {
  isActive: boolean;
}

const NavButtonContainer = styled.TouchableOpacity<NavButtonProps>`
  display: flex;
  align-items: center;
  height: 55px;
  justify-content: space-between;
`;

const NavButtonText = styled.Text<NavButtonProps>`
  font-family: ${({isActive}) => (isActive ? 'SCDream6' : 'SCDream4')};
  font-size: 15px;
  color: ${({theme, isActive}) =>
    isActive ? theme.colors.maindarkorange : theme.colors.deepgray};
`;

const ShareButton = styled.TouchableOpacity`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: ${({theme}) => theme.colors.maindarkorange};
  width: 70px;
  height: 70px;
  border-radius: 50px;
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-35px, -35px);
  z-index: 1;
`;

const Navigation: React.FC<BottomTabBarProps> = ({state, navigation}) => {
  const theme = useTheme();

  return (
    <Container>
      <ShareButton>
        <FeatherIcon name={'upload'} size={30} color={theme.colors.white} />
      </ShareButton>
      <Shadow distance={10} startColor={'rgba(0, 0, 0, 0.1)'}>
        <BoxContainer>
          {state.routes.map((route, index) => {
            const isFocused = state.index === index;

            const onPress = () => {
              const event = navigation.emit({
                type: 'tabPress',
                target: route.key,
                canPreventDefault: true,
              });

              if (!isFocused && !event.defaultPrevented) {
                navigation.navigate(route.name);
              }
            };

            return (
              <NavButtonContainer
                key={route.key}
                onPress={onPress}
                isActive={isFocused}
              >
                <EntypoIcon
                  name={
                    route.name === 'HomeStack' ? 'folder-images' : 'emoji-happy'
                  }
                  size={30}
                  color={
                    isFocused
                      ? theme.colors.maindarkorange
                      : theme.colors.deepgray
                  }
                />
                <NavButtonText isActive={isFocused}>
                  {route.name === 'HomeStack' ? '모아' : '마이'}
                </NavButtonText>
              </NavButtonContainer>
            );
          })}
        </BoxContainer>
      </Shadow>
    </Container>
  );
};

export default Navigation;

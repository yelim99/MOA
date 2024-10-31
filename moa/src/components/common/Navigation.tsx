import React from 'react';
import Icon from 'react-native-vector-icons/Entypo';
import styled, {useTheme} from 'styled-components/native';
import {BottomTabBarProps} from '@react-navigation/bottom-tabs';

const Container = styled.View`
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  border-top-width: 1px;
  border-top-color: ${({theme}) => theme.colors.deepgray};
  background-color: ${({theme}) => theme.colors.lightgray};
`;

const BoxContainer = styled.View`
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
  padding: 10px 0;
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
  font-weight: 500;
  font-size: 15px;
  color: ${({theme, isActive}) =>
    isActive ? theme.colors.maindarkorange : theme.colors.deepgray};
`;

const Navigation: React.FC<BottomTabBarProps> = ({state, navigation}) => {
  const theme = useTheme();

  return (
    <Container>
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
              <Icon
                name={route.name === 'Home' ? 'folder-images' : 'emoji-happy'}
                size={30}
                color={
                  isFocused
                    ? theme.colors.maindarkorange
                    : theme.colors.deepgray
                }
              />
              <NavButtonText isActive={isFocused}>
                {route.name === 'Home' ? '모아' : '마이'}
              </NavButtonText>
            </NavButtonContainer>
          );
        })}
      </BoxContainer>
    </Container>
  );
};

export default Navigation;

// src/components/common/button/IconButton.tsx
import React from 'react';
import {TouchableOpacityProps} from 'react-native';
import styled, {css, useTheme, DefaultTheme} from 'styled-components/native';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';
import FeatherIcon from 'react-native-vector-icons/Feather';

interface TextButtonProps extends TouchableOpacityProps {
  iconName?: string;
  iconSet?: 'Material' | 'Feather';
  size?: 'small' | 'medium' | 'large';
  backcolor?: keyof DefaultTheme['colors'];
  onPress?: () => void;
  text: string;
}

const StyledTextButton = styled.TouchableOpacity<{
  backcolor: keyof DefaultTheme['colors'];
  size: 'small' | 'medium' | 'large';
}>`
  background-color: ${({backcolor, theme}) => theme.colors[backcolor]};
  ${({size}) =>
    size === 'small' &&
    css`
      width: 80px;
      height: 25px;
      padding: 5px;
    `}
  ${({size}) =>
    size === 'medium' &&
    css`
      width: 160px;
      height: 30px;
      padding: 5px;
    `}
  ${({size}) =>
    size === 'large' &&
    css`
      height: 50px;
      width: 320px;
      padding: 5px 30px;
    `}
  border-radius: 15px;
  align-items: center;
  justify-content: center;
  flex-direction: row;
`;

const ButtonText = styled.Text<{
  textcolor: string;
  size: 'small' | 'medium' | 'large';
}>`
  color: ${({textcolor}) => textcolor};
  font-size: ${({theme, size}) =>
    size === 'small'
      ? theme.fontSize.small
      : size === 'medium'
        ? theme.fontSize.regular
        : theme.fontSize.large};
  font-family: ${({theme, size}) =>
    size === 'small'
      ? theme.fontFamily.SCDream4
      : size === 'medium'
        ? theme.fontFamily.SCDream6
        : theme.fontFamily.SCDream7};
  margin-left: 5px;
`;

export const TextButton: React.FC<TextButtonProps> = ({
  iconName,
  iconSet = 'Material',
  backcolor = 'white',
  size = 'medium',
  onPress,
  text,
  ...props
}) => {
  const theme = useTheme();
  const iconColor =
    backcolor === 'white' || backcolor === 'mainlightyellow'
      ? theme.colors.maindarkorange
      : theme.colors.white;
  const TextComponent = iconSet === 'Feather' ? FeatherIcon : MaterialIcon;
  return (
    <StyledTextButton
      size={size}
      backcolor={backcolor}
      onPress={onPress}
      {...props}
    >
      {iconName && (
        <TextComponent name={iconName} size={12} color={iconColor} />
      )}
      <ButtonText textcolor={iconColor} size={size}>
        {text}
      </ButtonText>
    </StyledTextButton>
  );
};

// src/components/common/button/Button.tsx
import React from 'react';
import {TouchableOpacityProps, StyleProp, ViewStyle} from 'react-native';
import {StyledButton, ButtonText, ButtonIcon} from './Buttons.styles';

interface ButtonProps extends TouchableOpacityProps {
  text?: string;
  icon?: JSX.Element;
  variant?: 'primary' | 'secondary' | 'outline' | 'icon' | 'rounded';
  size?: 'small' | 'medium' | 'large';
  styleOverride?: StyleProp<ViewStyle>;
  onPress?: () => void;
}

const Button: React.FC<ButtonProps> = ({
  text,
  icon,
  variant = 'primary', // 기본값 설정
  size = 'medium', // 기본값 설정
  styleOverride,
  onPress,
  ...props
}) => {
  return (
    <StyledButton
      variant={variant}
      size={size}
      style={styleOverride}
      onPress={onPress}
      {...props}
    >
      {icon && <ButtonIcon>{icon}</ButtonIcon>}
      {text && <ButtonText variant={variant}>{text}</ButtonText>}
    </StyledButton>
  );
};

export default Button;

// src/components/common/button/Button.tsx
import React from 'react';
import {TouchableOpacityProps, StyleProp, ViewStyle} from 'react-native';
import {StyledButton, ButtonText, ButtonIcon} from './Buttons.styles';

interface ButtonProps extends TouchableOpacityProps {
  text?: string;
  icon?: JSX.Element;
  content?: 'icon' | 'text' | 'colorSelect';
  size?: 'small' | 'medium' | 'large'; // 텍스트 버튼의 사이즈
  backcolor?: 'white' | 'mainlightorange' | 'maindarkorange' | string; // 배경색
  textcolor?: 'white' | 'maindarkorange'; // 글자색
  styleOverride?: StyleProp<ViewStyle>;
  onPress?: () => void; //  기능
}

const Button: React.FC<ButtonProps> = ({
  text,
  icon,
  content = 'text', // 기본값 설정
  size = 'small', // 기본값 설정
  backcolor = 'white', // 기본값 설정
  textcolor,
  styleOverride,
  onPress,
  ...props
}) => {
  return (
    <StyledButton
      content={content}
      size={size}
      backcolor={backcolor}
      style={styleOverride}
      onPress={onPress}
      {...props}
    >
      {icon && <ButtonIcon>{icon}</ButtonIcon>}
      {content === 'text' && text && (
        <ButtonText content="text" backcolor={backcolor}>
          {text}
        </ButtonText>
      )}
    </StyledButton>
  );
};

export default Button;

// src/components/common/button/Button.styles.ts
import styled, {css} from 'styled-components/native';

export const StyledButton = styled.TouchableOpacity<{
  content: 'icon' | 'text' | 'colorSelect';
  size: 'small' | 'medium' | 'large'; // 텍스트 버튼 전용
  backcolor?: string;
}>`
  ${({content, backcolor, theme}) =>
    content === 'icon' && // 아이콘만 있는 버튼
    css`
      background-color: ${backcolor === 'maindarkorange'
        ? theme.colors.maindarkorange
        : theme.colors.white};
      border-radius: 100%;
      width: 30px;
      height: 30px;  
      align-items: center;
      justify-content: center;
    `}
  ${({content, theme, backcolor}) =>
    content === 'text' && //텍스트가 있는 버튼
    css`
      background-color: ${backcolor === 'maindarkorange'
        ? theme.colors.maindarkorange
        : backcolor === 'mainlightorange'
        ? theme.colors.mainlightorange
        : theme.colors.white};
      border-radius: 15px;
      align-items: center;
      justify-content: center;
      padding: 5px;
    `}
  ${({content, backcolor}) =>
    content === 'colorSelect' && // 사용자가 선택하는 색상 버튼
    css`
      background-color: ${backcolor};
      border-radius: 50px;
      width: 40px;
      height: 40px;
      align-items: center;
      justify-content: center;
    `}
  ${({size}) =>
    size === 'small' &&
    css`
      width: 90px;
      height: 25px;
    `}
  ${({size}) =>
    size === 'medium' &&
    css`
      width: 210px;
      height: 40px;
    `}
  ${({size}) =>
    size === 'large' &&
    css`
      width: 410px;
      height: 60px;
    `}
  align-items: center;
  flex-direction: row;
  justify-content: center;
`;

export const ButtonText = styled.Text<{
  content: 'text' | 'icon';
  backcolor?: 'white' | 'mainlightorange' | 'maindarkorange';
}>`
  ${({ content, backcolor, theme }) =>
    content === 'text' &&
    css`
      color: ${backcolor === 'white'
        ? theme.colors.maindarkorange
        : theme.colors.white};
    `}
  font-size: ${({ theme }) => theme.fontSize.regular};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const ButtonIcon = styled.View<{color?: string}>`
  color: ${({theme, color}) => color || theme.colors.maindarkorang};
`;

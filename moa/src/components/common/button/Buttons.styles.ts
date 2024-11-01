// src/components/common/button/Button.styles.ts
import styled, {css} from 'styled-components/native';

export const StyledButton = styled.TouchableOpacity<{
  variant: string;
  size: string;
  color: string;
}>`
  ${({variant, theme}) =>
    variant === 'icon' && // 아이콘이 있는 버튼
    css`
      background-color: ${theme.colors.white};
      border-radius: 8px;
    `}
  ${({variant, theme}) =>
    variant === 'text' && //텍스트가 있는 버튼
    css`
      background-color: ${theme.colors.mainlightorange};
    `}
  ${({variant, theme}) =>
    variant === 'outline' &&
    css`
      background-color: transparent;
      border: 1px solid ${theme.colors.maindarkorange};
    `}
  ${({size}) =>
    size === 'small' &&
    css`
      padding: 6px 12px;
    `}
  ${({size}) =>
    size === 'medium' &&
    css`
      padding: 10px 20px;
    `}
  ${({size}) =>
    size === 'large' &&
    css`
      padding: 14px 28px;
    `}
  align-items: center;
  flex-direction: row;
  justify-content: center;
`;

export const ButtonText = styled.Text<{variant: string}>`
  ${({variant, theme}) =>
    variant === 'outline'
      ? css`
          color: ${theme.colors.maindarkorange};
        `
      : css`
          color: ${theme.colors.white};
        `}
  font-size: ${({theme}) => theme.fontSize.regular};
  font-weight: ${({theme}) => theme.fontWeight.bold};
`;

export const ButtonIcon = styled.View<{color?: string}>`
  color: ${({theme, color}) => color || theme.colors.maindarkorang};
`;

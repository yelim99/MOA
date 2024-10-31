// src/styles/GlobalStyles.ts
import {css} from 'styled-components/native';

export const GlobalStyles = css`
  color: ${({theme}) => theme.colors.text};
  font-family: 'Roboto';
  font-size: 16px;
`;

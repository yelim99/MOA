import React from 'react';
import styled from 'styled-components/native';

const InputContainer = styled.TextInput`
  width: 100%;
  height: 40px;
  background-color: ${({theme}) => theme.colors.white};
  padding: 10px;
  font-family: SCDream4;
`;

const TextInput = () => {
  return <InputContainer />;
};

export default TextInput;

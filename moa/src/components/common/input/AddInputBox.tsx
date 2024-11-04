import {View, Text} from 'react-native';
import React, {ReactNode} from 'react';
import styled from 'styled-components/native';

const Container = styled.View`
  width: 100%;
  height: 110px;
  background-color: ${({theme}) => theme.colors.lightgray};
  padding: 20px;
  display: flex;
  justify-content: space-between;
`;

const Label = styled.Text`
  font-family: SCDream4;
  font-size: 16px;
`;

interface AddInputBoxProps {
  label: string;
  children: ReactNode;
}

const AddInputBox = ({label, children}: AddInputBoxProps) => {
  return (
    <Container>
      <Label>{label}</Label>
      {children}
    </Container>
  );
};

export default AddInputBox;

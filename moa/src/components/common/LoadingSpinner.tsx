import React from 'react';
import styled, {useTheme} from 'styled-components/native';
import {ActivityIndicator} from 'react-native';

const LoadingOverlay = styled.View`
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
`;

const LoadingSpinner = () => {
  const theme = useTheme();

  return (
    <LoadingOverlay>
      <ActivityIndicator size="large" color={theme.colors.maindarkorange} />
    </LoadingOverlay>
  );
};

export default LoadingSpinner;

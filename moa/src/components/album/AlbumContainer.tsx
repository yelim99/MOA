import React from 'react';
import styled from 'styled-components/native';

const Container = styled.View`
  width: 100%;
`;

const TitleLine = styled.View`
  flex-direction: row;
  align-items: center;
  margin-bottom: 15px;
`;

const Title = styled.Text`
  font-family: SCDream5;
  font-size: 15px;
  margin-right: 10px;
`;

const TitleNum = styled(Title)`
  color: ${({theme}) => theme.colors.maindarkorange};
`;

interface AlbumContainerProps {
  title: string;
}

const AlbumContainer = ({title}: AlbumContainerProps) => {
  return (
    <Container>
      <TitleLine>
        <Title>{title}</Title>
        <TitleNum>255장</TitleNum>
      </TitleLine>
    </Container>
  );
};

export default AlbumContainer;

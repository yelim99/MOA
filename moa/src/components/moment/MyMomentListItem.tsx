import React from 'react';
import styled from 'styled-components/native';
import {MomentInfo} from '../../types/moment';

const Container = styled.TouchableOpacity`
  width: 100%;
  height: 80px;
  border-radius: 20px;
  border-style: solid;
  border-width: 1px;
  border-color: ${({theme}) => theme.colors.mainlightorange};
  display: flex;
  padding: 14px;
  justify-content: space-between;
  margin-bottom: 15px;
`;

const TitleLine = styled.View`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  align-items: center;
`;

const Title = styled.Text`
  font-family: SCDream5;
  font-size: 18px;
  max-width: 70%;
  overflow: hidden;
`;

const Date = styled.Text`
  font-family: SCDream3;
  font-size: 15px;
`;

const Owner = styled.Text`
  font-family: SCDream5;
  font-size: 15px;
  color: ${({theme}) => theme.colors.deepgray};
`;

interface MyMomentListItemProps {
  momentInfo: MomentInfo;
}

const MyMomentListItem = ({momentInfo}: MyMomentListItemProps) => {
  return (
    <Container>
      <TitleLine>
        <Title numberOfLines={1} ellipsizeMode="tail">
          {momentInfo.momentTitle}
        </Title>
        <Date>{momentInfo.createdAt}</Date>
      </TitleLine>
      <Owner>{momentInfo.momentOwner}</Owner>
    </Container>
  );
};

export default MyMomentListItem;

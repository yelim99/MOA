import React from 'react';
import styled from 'styled-components/native';

const Container = styled.View<{itemSize: number}>`
  width: ${({itemSize}) => itemSize}px;
  height: 100px;
  align-items: center;
  justify-content: space-between;
  margin-right: 15px;
`;

const StyledImage = styled.Image<{itemSize: number}>`
  width: ${({itemSize}) => itemSize}px;
  height: ${({itemSize}) => itemSize}px;
  border: 2px solid ${({theme}) => theme.colors.mediumgray};
  border-radius: 100px;
`;

const Name = styled.Text`
  font-family: 'SCDream5';
  font-size: 12px;
  color: ${({theme}) => theme.colors.deepgray};
`;

interface MemberListItemProps {
  userName: string;
  userImage: string;
  itemSize: number;
}

const MemberListItem = ({
  userName,
  userImage,
  itemSize,
}: MemberListItemProps) => {
  return (
    <Container itemSize={itemSize}>
      <StyledImage
        source={{uri: userImage}}
        resizeMode="contain"
        itemSize={itemSize}
      />
      <Name>{userName}</Name>
    </Container>
  );
};

export default MemberListItem;

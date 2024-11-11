import React, {useState} from 'react';
import styled from 'styled-components/native';
import MemberListItem from './MemberListItem';
import {Profile} from '../../types/user';
import {FlatList} from 'react-native';
import {Member} from '../../types/moment';

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

const TitleNum = styled(Title)<{darkColor: string}>`
  color: ${({darkColor, theme}) => darkColor || theme.colors.maindarkorange};
`;

interface MemberListProps {
  memberList: Member[];
  darkColor?: string;
}

const MemberList = ({memberList, darkColor = ''}: MemberListProps) => {
  const [containerWidth, setContainerWidth] = useState(0);

  const itemSize = (containerWidth - 3 * 15) / 4;

  return (
    <Container
      onLayout={(event) => {
        const {width} = event.nativeEvent.layout;
        setContainerWidth(width);
      }}
    >
      <TitleLine>
        <Title>참여중인 멤버</Title>
        <TitleNum darkColor={darkColor}>{memberList.length}명</TitleNum>
      </TitleLine>
      <FlatList
        data={memberList}
        horizontal={true}
        showsHorizontalScrollIndicator={false}
        keyExtractor={(item) => item.userId.toString()}
        renderItem={({item}) => (
          <MemberListItem
            userName={item.nickname}
            userImage={item.imageSrc}
            itemSize={itemSize}
          />
        )}
      />
    </Container>
  );
};

export default MemberList;

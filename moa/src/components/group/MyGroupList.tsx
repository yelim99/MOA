import React from 'react';
import MyGroupListItem from './MyGroupListItem';
import styled from 'styled-components/native';

const Container = styled.View`
  width: 100%;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 16px;
  justify-content: space-between;
`;

const MyGroupList = () => {
  // 테스트 데이터 (나중에 삭제 예정)
  const groupList = [
    {
      groupId: 1,
      groupName: '싸피 7반',
      color: 'red',
      iconName: 'book-open',
    },
    {
      groupId: 2,
      groupName: '자율 602',
      color: 'yellow',
      iconName: 'book-open',
    },
    {
      groupId: 3,
      groupName: '친구 모임',
      color: 'green',
      iconName: 'chat',
    },
    {
      groupId: 4,
      groupName: '삼성전자',
      color: 'purple',
      iconName: 'briefcase',
    },
    {
      groupId: 5,
      groupName: '여자친구',
      color: 'pink',
      iconName: 'heart',
    },
    {
      groupId: 6,
      groupName: '동호회',
      color: 'blue',
      iconName: 'chat',
    },
  ];

  return (
    <Container>
      {groupList.map((group) => (
        <MyGroupListItem key={group.groupId} groupInfo={group} />
      ))}
    </Container>
  );
};

export default MyGroupList;

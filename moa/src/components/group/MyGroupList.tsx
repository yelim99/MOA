import React from 'react';
import MyGroupListItem from './MyGroupListItem';
import styled from 'styled-components/native';

const Container = styled.View`
  width: 100%;
  display: flex;
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
      groupColor: 'red',
      groupIcon: 'book-open',
    },
    {
      groupId: 2,
      groupName: '자율 602',
      groupColor: 'yellow',
      groupIcon: 'book-open',
    },
    {
      groupId: 3,
      groupName: '친구 모임',
      groupColor: 'green',
      groupIcon: 'chat',
    },
    {
      groupId: 4,
      groupName: '삼성전자',
      groupColor: 'purple',
      groupIcon: 'briefcase',
    },
    {
      groupId: 7,
      groupName: '동호회',
      groupColor: 'blue',
      groupIcon: 'chat',
    },
    {
      groupId: 5,
      groupName: '여자친구',
      groupColor: 'pink',
      groupIcon: 'heart',
    },
    {
      groupId: 6,
      groupName: '동호회입니다 오버플로우 테스트',
      groupColor: 'blue',
      groupIcon: 'chat',
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

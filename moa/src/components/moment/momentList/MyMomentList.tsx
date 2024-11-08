import React from 'react';
import MyMomentListItem from './MyMomentListItem';
import styled from 'styled-components/native';

const Container = styled.View`
  width: 100%;
`;

const MyMomentList = () => {
  const momentList = [
    {
      momentId: '1',
      momentName: '싸피 가을 마라톤',
      momentOwner: '김관리',
      createdAt: '2024-10-20',
    },
    {
      momentId: '2',
      momentName: '삼성 어린이집 운동회',
      momentOwner: '박선생',
      createdAt: '2024-10-17',
    },
    {
      momentId: '3',
      momentName: '어쩌고 해커톤',
      momentOwner: '이매니저',
      createdAt: '2024-11-01',
    },
    {
      momentId: '4',
      momentName: '용문사 템플스테이',
      momentOwner: '최스님',
      createdAt: '2024-11-15',
    },
    {
      momentId: '5',
      momentName: '삼성 어린이집 운동회입니다 오버플로우테스트',
      momentOwner: '박선생',
      createdAt: '2024-10-17',
    },
    {
      momentId: '6',
      momentName: '어쩌고 해커톤',
      momentOwner: '이매니저',
      createdAt: '2024-11-01',
    },
    {
      momentId: '7',
      momentName: '용문사 템플스테이',
      momentOwner: '최스님',
      createdAt: '2024-11-15',
    },
  ];

  return (
    <Container>
      {momentList.map((moment) => (
        <MyMomentListItem key={moment.momentId} momentInfo={moment} />
      ))}
    </Container>
  );
};

export default MyMomentList;

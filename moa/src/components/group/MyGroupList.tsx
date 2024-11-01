import {View, Text} from 'react-native';
import React from 'react';
import MyGroupListItem from './MyGroupListItem';
import styled, {useTheme} from 'styled-components/native';

const Container = styled.View`
  width: 100%;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 16px;
  justify-content: space-between;
`;

const MyGroupList = () => {
  return (
    <Container>
      <MyGroupListItem />
      <MyGroupListItem />
      <MyGroupListItem />
      <MyGroupListItem />
    </Container>
  );
};

export default MyGroupList;

import {Text} from 'react-native';
import React from 'react';
import ScreenContainer from '../../components/common/ScreenContainer';
import AddInputBox from '../../components/common/input/AddInputBox';
import TextInput from '../../components/common/input/TextInput';

const GroupAdd = () => {
  return (
    <ScreenContainer>
      <AddInputBox label="그룹 이름">
        <TextInput />
      </AddInputBox>
    </ScreenContainer>
  );
};

export default GroupAdd;

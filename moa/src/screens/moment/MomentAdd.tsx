import React, {useState} from 'react';
import ScreenContainer from '../../components/common/ScreenContainer';
import AddInputBox from '../../components/common/input/AddInputBox';
import {Picker} from '@react-native-picker/picker';
import styled from 'styled-components/native';

const MomentAdd = () => {
  const [momentName, setMomentName] = useState('');
  const [momentDescription, setMomentDescription] = useState('');
  const [momentOption, setMomentOption] = useState('');

  return (
    <ScreenContainer>
      <AddInputBox
        label="순간 이름"
        value={momentName}
        onChangeText={(text) => setMomentName(text)}
      />
      <AddInputBox
        label="순간 설명"
        value={momentDescription}
        onChangeText={(text) => setMomentDescription(text)}
      />
      <AddInputBox label="멤버 권한 설정" isText={false}>
        <Picker
          selectedValue={momentOption}
          onValueChange={(itemValue) => setMomentOption(itemValue)}
        >
          <Picker.Item label="나의 업로드만 허용" value="0" />
          <Picker.Item label="모든 멤버의 업로드 허용" value="1" />
        </Picker>
      </AddInputBox>
    </ScreenContainer>
  );
};

export default MomentAdd;

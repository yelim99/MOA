import React, {useState} from 'react';
import ScreenContainer from '../../components/common/ScreenContainer';
import AddInputBox from '../../components/common/input/AddInputBox';
import {Picker} from '@react-native-picker/picker';
import styled from 'styled-components/native';
import {TextButton} from '../../components/common/button/TextButton';

const PickerContainer = styled.View`
  width: 100%;
  border-bottom-width: 1px;
  border-bottom-color: ${({theme}) => theme.colors.maindarkorange};
`;

const StyledPicker = styled(Picker)`
  width: 100%;
  background-color: ${({theme}) => theme.colors.white};
  font-family: SCDream4;
  font-size: 18px;
  border-bottom-width: 1px;
  border-bottom-color: ${({theme}) => theme.colors.maindarkorange};
`;

const ButtonContainer = styled.View`
  width: 100%;
  margin-top: 20px;
  display: flex;
  align-items: center;
`;

const MomentAdd = () => {
  const [momentName, setMomentName] = useState('');
  const [momentDescription, setMomentDescription] = useState('');
  const [momentOption, setMomentOption] = useState('0');

  const handleMomentPost = async () => {
    if (momentName === '' || momentDescription === '') {
      return;
    }
  };

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
        <PickerContainer>
          <StyledPicker
            selectedValue={momentOption}
            onValueChange={(itemValue) => setMomentOption(itemValue as string)}
          >
            <Picker.Item label="나의 업로드만 허용" value="0" />
            <Picker.Item label="모든 멤버의 업로드 허용" value="1" />
          </StyledPicker>
        </PickerContainer>
      </AddInputBox>
      <ButtonContainer>
        <TextButton
          text="순간 만들기"
          size="large"
          backcolor="maindarkorange"
          onPress={handleMomentPost}
        />
      </ButtonContainer>
    </ScreenContainer>
  );
};

export default MomentAdd;

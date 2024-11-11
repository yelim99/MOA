import React, {useState} from 'react';
import ScreenContainer from '../../components/common/ScreenContainer';
import AddInputBox from '../../components/common/input/AddInputBox';
import {Picker} from '@react-native-picker/picker';
import styled from 'styled-components/native';
import {TextButton} from '../../components/common/button/TextButton';
import api from '../../utils/api';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import {Alert} from 'react-native';
import {HomeScreenNavigationProp} from '../../types/screen';
import {useNavigation} from '@react-navigation/native';

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
  const [loading, setLoading] = useState(false);
  const [momentName, setMomentName] = useState('');
  const [momentDescription, setMomentDescription] = useState('');
  const [uploadOption, setUploadOption] = useState('all');

  const navigation = useNavigation<HomeScreenNavigationProp>();

  const handleMomentPost = async () => {
    if (momentName === '' || momentDescription === '') {
      Alert.alert('정보 입력', '모든 정보를 정확히 입력해주세요.');
      return;
    }

    const newMoment = {
      momentName: momentName,
      momentDescription: momentDescription,
      uploadOption: uploadOption,
    };

    setLoading(true);

    try {
      const response = await api.post('/moment', newMoment);
      Alert.alert('순간 생성', `${momentName} 순간이 생성되었습니다.`);
      navigation.navigate('MomentDetail', {
        momentInfo: {
          momentId: response.data?.momentId,
          momentName: momentName,
        },
      });
    } catch (error) {
      Alert.alert('순간 생성 오류', '순간 생성 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
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
            selectedValue={uploadOption}
            onValueChange={(itemValue) => setUploadOption(itemValue as string)}
          >
            {/* 나의 업로드만 허용 나중에 만들기로 함 -> 백에서 안됨 */}
            <Picker.Item label="나의 업로드만 허용" value="all" />
            <Picker.Item label="모든 멤버의 업로드 허용" value="al" />
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
      {loading && <LoadingSpinner />}
    </ScreenContainer>
  );
};

export default MomentAdd;

/* eslint-disable react-native/no-inline-styles */
import React, {useState} from 'react';
import {Modal, Text} from 'react-native';
import styled, {useTheme} from 'styled-components/native';
import {launchCamera, launchImageLibrary} from 'react-native-image-picker';
import {TextButton} from '../common/button/TextButton';

const Container = styled.View`
  width: 100%;
`;

const Title = styled.View`
  flex-direction: row;
  justify-content: space-between;
  margin-top: 20px;
`;

const ModalBackground = styled.View`
  flex: 1;
  justify-content: flex-end;
  background-color: rgba(0, 0, 0, 0.5);
`;

const ModalContainer = styled.View`
  background-color: white;
  padding: 20px;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
`;

const OptionButton = styled.TouchableOpacity`
  padding: 15px;
  border-bottom-width: 1px;
  border-bottom-color: #ddd;
`;

const OptionText = styled.Text`
  font-size: 18px;
  font-family: ${(props) => props.theme.fontFamily.SCDream4};
`;

const CancelButton = styled.TouchableOpacity`
  padding: 15px;
  align-items: center;
`;

const CancelText = styled.Text`
  font-size: 20px;
  font-family: ${(props) => props.theme.fontFamily.SCDream4};
  color: ${(props) => props.theme.colors.maindarkorange};
`;

const PreviewContainer = styled.View`
  margin-top: 20px;
  align-items: center;
  width: 100%;
`;

const PlaceholderPreview = styled.View`
  width: 100%;
  height: 300px;
  border-radius: 10px;
  border: 1px solid ${(props) => props.theme.colors.lightgray};
  background-color: #e0e0e0;
  justify-content: center;
  align-items: center;
`;

const PlaceholderText = styled.Text`
  color: ${(props) => props.theme.colors.gray};
  font-size: 16px;
  font-family: ${(props) => props.theme.fontFamily.SCDream4};
`;

const PreviewImage = styled.Image`
  width: 100%;
  height: 300px;
  border-radius: 10px;
  border: 1px solid ${(props) => props.theme.colors.lightgray};
`;

const MyComponent = () => {
  // 모달 상태
  const [modalVisible, setModalVisible] = useState(false);
  const [imageUri, setImageUri] = useState<string | null>(null);
  const theme = useTheme();

  const handleSelectOption = (option: string) => {
    // 클릭시 modaVisible상태가 false로 변경됨
    setModalVisible(false);
    // 카메라 선택시
    if (option === 'camera') {
      console.log('되고있음?');
      launchCamera({mediaType: 'photo'}, (response) => {
        console.log('Camera response있냐?:', response);
        if (response.didCancel) {
          console.log('카메라 취소');
        } else if (response.errorMessage) {
          console.error('ImagePicker Error: ', response.errorMessage);
        } else {
          const uri = response.assets?.[0].uri || null;
          setImageUri(uri);
          console.log('Photo taken:', uri);
          //   console.log('Photo taken:', response.assets?.[0].uri);
        }
      });
    } else if (option === 'gallery') {
      // 사진 갤러리 선택시
      launchImageLibrary({mediaType: 'photo'}, (response) => {
        if (response.didCancel) {
          console.log('사진 선택 취소');
        } else if (response.errorMessage) {
          console.error('ImagePicker Error: ', response.errorMessage);
        } else {
          const uri = response.assets?.[0].uri || null;
          setImageUri(uri);
          console.log('Photo selected:', uri);
        }
      });
    }
  };

  return (
    <Container>
      <Title>
        <Text style={{fontSize: 18, fontFamily: theme.fontFamily.SCDream6}}>
          얼굴 등록
        </Text>
        <TextButton
          backcolor="mainlightyellow"
          text="등록하기"
          iconName="photo-camera"
          iconSet="Material"
          size="small"
          onPress={() => setModalVisible(true)}
        />
      </Title>
      <Modal
        transparent={true}
        animationType="slide"
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
      >
        <ModalBackground>
          <ModalContainer>
            <OptionButton onPress={() => handleSelectOption('camera')}>
              <OptionText>사진 촬영</OptionText>
            </OptionButton>
            <OptionButton onPress={() => handleSelectOption('gallery')}>
              <OptionText>갤러리에서 선택</OptionText>
            </OptionButton>
            <CancelButton onPress={() => setModalVisible(false)}>
              <CancelText>취소</CancelText>
            </CancelButton>
          </ModalContainer>
        </ModalBackground>
      </Modal>

      <PreviewContainer>
        {imageUri ? (
          <PreviewImage source={{uri: imageUri}} />
        ) : (
          <PlaceholderPreview>
            <PlaceholderText>미리보기가 여기에 표시됩니다</PlaceholderText>
          </PlaceholderPreview>
        )}
      </PreviewContainer>
    </Container>
  );
};

export default MyComponent;

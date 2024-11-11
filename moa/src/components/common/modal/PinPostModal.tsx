import React, {useRef} from 'react';
import StyledModal from './StyledModal';
import styled from 'styled-components/native';
import {TextInput} from 'react-native';
import {TextButton} from '../button/TextButton';

const ContentContainer = styled.View`
  padding: 30px;
  align-items: center;
  justify-content: center;
  position: relative;
`;

const Title = styled.Text`
  font-family: 'SCDream5';
  font-size: 20px;
  color: ${({theme}) => theme.colors.maindarkorange};
  margin-bottom: 30px;
`;

const PinContainer = styled.View`
  width: 90%;
  flex-direction: row;
  justify-content: space-between;
`;

const NumberContainer = styled.View`
  width: 40px;
  height: 50px;
  border-radius: 10px;
  background-color: ${({theme}) => theme.colors.mainlightyellow};
  align-items: center;
  justify-content: center;
  margin: 0 5px;
`;

const PinInput = styled(TextInput)`
  font-family: SCDream4;
  font-size: 18px;
  text-align: center;
  width: 100%;
  height: 100%;
`;

interface PinModalProps {
  pinNum: string;
  isModalVisible: boolean;
  toggleModal: () => void;
  setPinNum: (value: string) => void;
}

const PinModal = ({
  isModalVisible,
  toggleModal,
  pinNum,
  setPinNum,
}: PinModalProps) => {
  const inputRefs = useRef<Array<TextInput | null>>([]);

  const handleChangeText = (text: string, index: number) => {
    const newPin = pinNum.split('');
    newPin[index] = text;
    setPinNum(newPin.join(''));

    if (text && index < 5) {
      inputRefs.current[index + 1]?.focus(); // Move to next input on input
    }
  };

  return (
    <StyledModal isModalVisible={isModalVisible} toggleModal={toggleModal}>
      <ContentContainer>
        <Title>PIN 번호 입력</Title>
        <PinContainer>
          {Array.from({length: 6}).map((_, index) => (
            <NumberContainer key={index}>
              <PinInput
                ref={(ref) => (inputRefs.current[index] = ref)}
                maxLength={1}
                keyboardType="number-pad"
                value={pinNum[index] || ''}
                onChangeText={(text) => handleChangeText(text, index)}
              />
            </NumberContainer>
          ))}
        </PinContainer>
      </ContentContainer>
    </StyledModal>
  );
};

export default PinModal;

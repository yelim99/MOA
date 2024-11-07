import React, {useState} from 'react';
import styled, {useTheme} from 'styled-components/native';
import PhotoList from './PhotoList';
import Icon from 'react-native-vector-icons/MaterialIcons';
import StyledModal from '../common/modal/StyledModal';

const Container = styled.View`
  width: 100%;
`;

const TitleLine = styled.View`
  flex-direction: row;
  align-items: center;
  margin-bottom: 15px;
`;

const Title = styled.Text`
  font-family: SCDream5;
  font-size: 15px;
  margin-right: 10px;
`;

const TitleNum = styled(Title)<{darkColor: string}>`
  color: ${({darkColor}) => darkColor};
`;

const ButtonLine = styled.View`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  align-items: center;
`;

const OptionButton = styled.TouchableOpacity`
  width: 130px;
  height: 30px;
  border: 1px solid ${({theme}) => theme.colors.deepgray};
  border-radius: 10px;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
`;

const OptionButtonText = styled.Text`
  font-family: SCDream4;
  font-size: 13px;
`;

const DownloadButton = styled.TouchableOpacity<{
  lightColor: string;
}>`
  width: 90px;
  height: 30px;
  border-radius: 10px;
  background-color: ${({lightColor}) => lightColor};
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
`;

const DownloadButtonText = styled.Text<{
  darkColor: string;
}>`
  font-family: SCDream5;
  font-size: 13px;
  color: ${({darkColor}) => darkColor};
`;

const ModalItemContainer = styled.TouchableOpacity`
  width: 100%;
  padding: 15px;
  align-items: center;
  justify-content: center;
`;

const ModalItem = styled.Text`
  font-family: SCDream4;
  font-size: 16px;
`;

interface AlbumContainerProps {
  title: string;
  lightColor: string;
  darkColor: string;
}

const AlbumContainer = ({
  title,
  lightColor,
  darkColor,
}: AlbumContainerProps) => {
  const theme = useTheme();

  const options = [
    {id: 'mine', label: '내가 나온 사진'},
    {id: 'scenary', label: '풍경'},
    {id: 'food', label: '음식'},
  ];

  const [isOptionModalVisible, setIsOptionModalVisible] = useState(false);

  const toggleOptionModal = () => {
    setIsOptionModalVisible(!isOptionModalVisible);
  };

  const handleSelectOption = (optionId: string) => {
    toggleOptionModal();

    if (optionId === 'mine') {
      toggleOptionModal();
    } else if (optionId === 'scenary') {
      toggleOptionModal();
    } else if (optionId === 'food') {
      toggleOptionModal();
    }
  };

  return (
    <Container>
      <TitleLine>
        <Title>{title}</Title>
        <TitleNum darkColor={darkColor}>255장</TitleNum>
      </TitleLine>
      <ButtonLine>
        <OptionButton onPress={toggleOptionModal}>
          <Icon name="monochrome-photos" size={15} color={theme.colors.black} />
          <OptionButtonText>사진 분류 옵션</OptionButtonText>
        </OptionButton>
        <DownloadButton lightColor={lightColor}>
          <Icon name="download" size={15} color={darkColor} />
          <DownloadButtonText darkColor={darkColor}>
            다운로드
          </DownloadButtonText>
        </DownloadButton>
      </ButtonLine>
      <StyledModal
        isModalVisible={isOptionModalVisible}
        toggleModal={toggleOptionModal}
      >
        {options.map((option) => (
          <ModalItemContainer
            key={option.id}
            onPress={() => handleSelectOption(option.id)}
          >
            <ModalItem>{option.label}</ModalItem>
          </ModalItemContainer>
        ))}
      </StyledModal>
      <PhotoList />
    </Container>
  );
};

export default AlbumContainer;

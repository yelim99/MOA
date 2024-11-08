import {TouchableOpacity} from 'react-native';
import React, {useState} from 'react';
import {MomentInfoDetail} from '../../../types/moment';
import styled, {useTheme} from 'styled-components/native';
import Icon from 'react-native-vector-icons/Ionicons';
import StyledModal from '../../common/modal/StyledModal';
import {onShare} from '../../../utils/share';
import PinModal from '../../common/modal/PinModal';

const Container = styled.View`
  width: 100%;
  border-radius: 20px;
  border: 1px solid ${({theme}) => theme.colors.maindarkorange};
  padding: 20px;
`;

const TitleLine = styled.View`
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
`;

const LeftTime = styled.Text`
  font-family: SCDream5;
  font-size: 15px;
  color: ${({theme}) => theme.colors.maindarkorange};
`;

const IconContainer = styled.View`
  flex-direction: row;
  width: 60px;
  justify-content: space-between;
  align-items: center;
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

const Description = styled.Text`
  width: 100%;
  text-align: center;
  font-family: SCDream4;
  font-size: 15px;
  margin: 20px 0;
`;

const TextLine = styled.View`
  flex-direction: row;
  align-items: center;
`;

const TextName = styled.Text`
  font-family: 'SCDream4';
  font-size: 13px;
  color: ${({theme}) => theme.colors.deepgray};
  margin-right: 10px;
`;

const TextContent = styled.Text`
  font-family: 'SCDream5';
  font-size: 13px;
  color: ${({theme}) => theme.colors.mainlightorange};
`;

interface MomentDetailHeaderProps {
  momentInfoDetail: MomentInfoDetail;
}

const MomentDetailHeader = ({momentInfoDetail}: MomentDetailHeaderProps) => {
  const [isOptionModalVisible, setOptionModalVisible] = useState(false);
  const [isPinModalVisible, setPinModalVisible] = useState(false);

  const toggleOptionModal = () => {
    setOptionModalVisible(!isOptionModalVisible);
  };

  const togglePinModal = () => {
    setPinModalVisible(!isPinModalVisible);
  };

  const options = [
    {id: 'pin', label: 'PIN번호 보기'},
    {id: 'put', label: '그룹 수정'},
    {id: 'delete', label: '그룹 삭제'},
  ];

  const handleSelectOption = (optionId: string) => {
    toggleOptionModal();

    if (optionId === 'pin') {
      toggleOptionModal();
      togglePinModal();
    } else if (optionId === 'put') {
      toggleOptionModal();
    } else if (optionId === 'delete') {
      toggleOptionModal();
    }
  };

  const theme = useTheme();

  // 임시 핀번호 -> 나중에 변경 예정
  const pinNum = '123456';

  return (
    <Container>
      <TitleLine>
        <LeftTime>남은 시간 타이머</LeftTime>
        <IconContainer>
          <TouchableOpacity
            onPress={() =>
              onShare(
                `${momentInfoDetail.momentName} 순간`,
                `moa://moment/${momentInfoDetail.momentId}`,
              )
            }
          >
            <Icon
              name="share-social-sharp"
              size={22}
              color={theme.colors.maindarkorange}
            />
          </TouchableOpacity>
          <TouchableOpacity onPress={toggleOptionModal}>
            <Icon
              name="ellipsis-vertical"
              size={22}
              color={theme.colors.maindarkorange}
            />
          </TouchableOpacity>
        </IconContainer>
      </TitleLine>
      <Description>{momentInfoDetail.momentDescription}</Description>
      <TextLine>
        <TextName>생성일</TextName>
        <TextContent>{momentInfoDetail.createdAt}</TextContent>
      </TextLine>
      <TextLine>
        <TextName>생성자</TextName>
        <TextContent>{momentInfoDetail.momentOwner}</TextContent>
      </TextLine>
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
      <PinModal
        pinNum={pinNum}
        isModalVisible={isPinModalVisible}
        toggleModal={togglePinModal}
      />
    </Container>
  );
};

export default MomentDetailHeader;
import React, {useState} from 'react';
import ScreenContainer from '../../components/common/ScreenContainer';
import AddInputBox from '../../components/common/input/AddInputBox';
import styled from 'styled-components/native';
import GroupIconButton from '../../components/common/button/GroupIconButton';
import {lightColorMap, darkColorMap} from '../../utils/groupColor';
import {TextButton} from '../../components/common/button/TextButton';

const InputContent = styled.View`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
`;

const ColorButton = styled.TouchableOpacity<{
  groupColor: string;
  bgColor: string;
  isSelected: boolean;
}>`
  width: 30px;
  height: 30px;
  border-radius: 50px;
  background-color: ${({bgColor}) => bgColor};
  border: 2px solid;
  border-color: ${({isSelected, theme, groupColor}) =>
    isSelected ? darkColorMap[groupColor] : theme.colors.white};
`;

const IconButtonContainer = styled.TouchableOpacity<{
  isSelected: boolean;
}>`
  width: 36px;
  height: 36px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50px;
  border: 4px solid
    ${({theme, isSelected}) =>
      isSelected ? theme.colors.white : 'transparent'};
`;

const ButtonContainer = styled.View`
  width: 100%;
  margin-top: 20px;
  display: flex;
  align-items: center;
`;

const GroupAdd = () => {
  const [groupName, setGroupName] = useState('');
  const [groupDescription, setGroupDescription] = useState('');
  const [groupColor, setGroupColor] = useState('gray');
  const [groupIcon, setGroupIcon] = useState('');

  const iconNameList = [
    'heart',
    'chat',
    'home',
    'book-open',
    'briefcase',
    'graduation-cap',
  ];

  const handleGroupPost = async () => {
    if (
      groupName === '' ||
      groupDescription === '' ||
      groupColor === 'gray' ||
      groupIcon === ''
    ) {
      return;
    }
  };

  return (
    <ScreenContainer>
      <AddInputBox
        label="그룹 이름"
        value={groupName}
        onChangeText={(text) => setGroupName(text)}
      />
      <AddInputBox
        label="그룹 설명"
        value={groupDescription}
        onChangeText={(text) => setGroupDescription(text)}
      />
      <AddInputBox label="색상 선택" isText={false}>
        <InputContent>
          {Object.entries(lightColorMap).map(([colorName, colorValue]) => (
            <ColorButton
              key={colorName}
              groupColor={colorName}
              bgColor={colorValue}
              isSelected={groupColor === colorName}
              onPress={() => setGroupColor(colorName)}
            />
          ))}
        </InputContent>
      </AddInputBox>
      <AddInputBox label="아이콘 선택" isText={false}>
        <InputContent>
          {iconNameList.map((iconName) => (
            <IconButtonContainer
              key={iconName}
              isSelected={groupIcon === iconName}
              onPress={() => setGroupIcon(iconName)}
            >
              <GroupIconButton
                color={groupColor}
                iconName={iconName}
                isAddPage={true}
                isSelected={iconName === groupIcon}
              />
            </IconButtonContainer>
          ))}
        </InputContent>
      </AddInputBox>
      <ButtonContainer>
        <TextButton
          text="그룹 만들기"
          size="large"
          backcolor="maindarkorange"
          onPress={handleGroupPost}
        />
      </ButtonContainer>
    </ScreenContainer>
  );
};

export default GroupAdd;

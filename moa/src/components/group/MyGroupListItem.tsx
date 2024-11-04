import React from 'react';
import styled, {useTheme} from 'styled-components/native';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';
import GroupIconButton from '../common/button/GroupIconButton';
import {GroupInfo} from '../../types/group';
import {darkColorMap, lightColorMap} from '../../utils/groupColor';

const Container = styled.TouchableOpacity<{bgColor: string}>`
  width: 46%;
  height: 150px;
  background-color: ${({bgColor}) => bgColor};
  padding: 14px;
  border-radius: 20px;
  display: flex;
  justify-content: space-between;
`;

const TopLine = styled.View`
  width: 100%;
`;

const TitleText = styled.Text`
  font-family: SCDream6;
  font-size: 20px;
  margin-bottom: 5px;
`;

const ContentText = styled.Text`
  font-family: SCDream4;
`;

const ContentColor = styled(ContentText)<{txtColor: string}>`
  color: ${({txtColor}) => txtColor};
  font-size: 16px;
`;

const BottomLine = styled.View`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin-top: 20px;
`;

const MemberLine = styled.View`
  display: flex;
  flex-direction: row;
  align-items: center;
`;
const MemberText = styled(ContentText)`
  color: ${({theme}) => theme.colors.deepgray};
  font-size: 14px;
  margin-left: 6px;
`;

interface MyGroupListItemProps {
  groupInfo: GroupInfo;
}

const MyGroupListItem = ({groupInfo}: MyGroupListItemProps) => {
  const theme = useTheme();

  return (
    <Container bgColor={lightColorMap[groupInfo.color]}>
      <TopLine>
        <TitleText>{groupInfo.groupName}</TitleText>
        <ContentColor txtColor={darkColorMap[groupInfo.color]}>
          사진 527장
        </ContentColor>
      </TopLine>
      <BottomLine>
        <MemberLine>
          <Icon
            name={'account-group'}
            size={20}
            color={theme.colors.deepgray}
          />
          <MemberText>25명</MemberText>
        </MemberLine>
        <GroupIconButton
          color={groupInfo.color}
          iconName={groupInfo.iconName}
        />
      </BottomLine>
    </Container>
  );
};

export default MyGroupListItem;

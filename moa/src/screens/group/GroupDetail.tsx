import {Text} from 'react-native';
import React, {useEffect} from 'react';
import ScreenContainer from '../../components/common/ScreenContainer';
import {RouteProp, useNavigation, useRoute} from '@react-navigation/native';
import {
  HomeStackParamList,
  StackHeaderNavigationProp,
} from '../../types/screen';
import StackHeader from '../../components/common/header/StackHeader';

type GroupDetailRouteProp = RouteProp<HomeStackParamList, 'GroupDetail'>;

const GroupDetail: React.FC = () => {
  const navigation = useNavigation<StackHeaderNavigationProp>();
  const route = useRoute<GroupDetailRouteProp>();

  const {groupId, groupName} = route.params.groupInfo || {
    groupId: '',
    groupName: '',
  };

  useEffect(() => {
    if (groupName) {
      navigation.setOptions({
        header: () => <StackHeader title={groupName} />,
      });
    }
  }, [groupName, navigation]);

  return (
    <ScreenContainer>
      <Text>{groupId}</Text>
    </ScreenContainer>
  );
};

export default GroupDetail;

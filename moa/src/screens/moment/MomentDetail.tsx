/* eslint-disable react/no-unstable-nested-components */
import React, {useEffect} from 'react';
import ScreenContainer from '../../components/common/ScreenContainer';
import MemberList from '../../components/member/MemberList';
import styled from 'styled-components/native';
import MomentDetailHeader from '../../components/moment/momentDetail/MomentDetailHeader';
import Partition from '../../components/common/Partition';
import {
  HomeStackParamList,
  StackHeaderNavigationProp,
} from '../../types/screen';
import {RouteProp, useNavigation, useRoute} from '@react-navigation/native';
import StackHeader from '../../components/common/header/StackHeader';
import AlbumContainer from '../../components/album/AlbumContainer';

const Container = styled.ScrollView.attrs({
  nestedScrollEnabled: true,
})`
  width: 100%;
`;

type MomentDetailRouteProp = RouteProp<HomeStackParamList, 'MomentDetail'>;

const MomentDetail: React.FC = () => {
  const navigation = useNavigation<StackHeaderNavigationProp>();
  const route = useRoute<MomentDetailRouteProp>();

  const {momentId, momentName} = route.params.momentInfo || {
    momentId: '',
    momentName: '',
  };

  const momentInfoDetail = {
    momentId: '1',
    momentName: '싸피 가을 마라톤',
    momentDescription: 'SSAFY 11기 가을 마라톤 대회 사진 공유',
    momentOwner: '김관리',
    createdAt: '2024-11-08',
  };

  useEffect(() => {
    if (momentName) {
      navigation.setOptions({
        header: () => <StackHeader title={momentName} />,
      });
    }
  }, [momentName, navigation]);

  return (
    <ScreenContainer>
      <Container>
        <MomentDetailHeader momentInfoDetail={momentInfoDetail} />
        <Partition />
        <MemberList />
        <Partition />
        <AlbumContainer title="공유된 사진" momentId={momentId} />
      </Container>
    </ScreenContainer>
  );
};

export default MomentDetail;

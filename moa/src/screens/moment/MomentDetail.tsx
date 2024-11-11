/* eslint-disable react/no-unstable-nested-components */
import React, {useCallback, useEffect, useState} from 'react';
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
import api from '../../utils/api';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import {MomentInfoDetail} from '../../types/moment';

const Container = styled.ScrollView.attrs({
  nestedScrollEnabled: true,
})`
  width: 100%;
`;

type MomentDetailRouteProp = RouteProp<HomeStackParamList, 'MomentDetail'>;

const MomentDetail: React.FC = () => {
  const navigation = useNavigation<StackHeaderNavigationProp>();
  const route = useRoute<MomentDetailRouteProp>();

  const [momentInfoDetail, setMomentInfoDetail] = useState<MomentInfoDetail>({
    momentId: '',
    momentName: '',
    momentOwner: '',
    momentDescription: '',
    createdAt: '',
  });
  const [loading, setLoading] = useState(false);
  const [isPinModalVisible, setIsPinModalVisible] = useState(false);

  const {momentId, momentName} = route.params.momentInfo || {
    momentId: '',
    momentName: '',
  };

  const toggleModal = useCallback(() => {
    setIsPinModalVisible((prev) => !prev);
  }, []);

  const getMomentDetail = useCallback(async () => {
    setLoading(true);

    try {
      const response = await api.get(`/moment/${momentId}`);
      setMomentInfoDetail(response.data);
    } catch (error: any) {
      if (error.response?.status === 403) {
        toggleModal();
      } else {
        console.error('데이터를 가져오는 중 오류 발생:', error);
      }
    } finally {
      setLoading(false);
    }
  }, [momentId, toggleModal]);

  useEffect(() => {
    getMomentDetail();
  }, [getMomentDetail]);

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
      {loading && <LoadingSpinner />}
    </ScreenContainer>
  );
};

export default MomentDetail;

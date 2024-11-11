import React, {useState, useEffect} from 'react';
import MyMomentListItem from './MyMomentListItem';
import styled from 'styled-components/native';
import LoadingSpinner from '../../common/LoadingSpinner';
import api from '../../../utils/api';
import {Alert} from 'react-native';
import {MomentInfo} from '../../../types/moment';

const Container = styled.View`
  width: 100%;
  align-items: center;
  padding-top: 1px;
`;

const NullText = styled.Text`
  font-family: 'SCDream4';
  font-size: 15px;
  color: ${({theme}) => theme.colors.deepgray};
`;

const MyMomentList = () => {
  const [loading, setLoading] = useState(false);
  const [momentList, setMomentList] = useState<MomentInfo[]>();

  const handleGetMomentList = async () => {
    setLoading(true);
    try {
      const response = await api.get('/moment');
      setMomentList(response?.data);
    } catch {
      Alert.alert(
        '나의 순간 오류',
        '나의 순간 목록을 불러오는 중 오류가 발생했습니다.',
      );
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    handleGetMomentList();
  }, []);

  return (
    <Container>
      {momentList?.map((moment) => (
        <MyMomentListItem key={moment.momentId} momentInfo={moment} />
      ))}
      {momentList?.length === 0 && <NullText>가입한 순간이 없습니다.</NullText>}
      {loading && <LoadingSpinner />}
    </Container>
  );
};

export default MyMomentList;

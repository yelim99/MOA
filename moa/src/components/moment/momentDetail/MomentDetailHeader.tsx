import {View, Text} from 'react-native';
import React from 'react';
import {MomentInfoDetail} from '../../../types/moment';

interface MomentDetailHeaderProps {
  momentInfoDetail: MomentInfoDetail;
}

const MomentDetailHeader = ({momentInfoDetail}: MomentDetailHeaderProps) => {
  return (
    <View>
      <Text>MomentDetailHeader</Text>
    </View>
  );
};

export default MomentDetailHeader;

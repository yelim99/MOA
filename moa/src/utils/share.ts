import {Share} from 'react-native';

export const onShare = async (name: string, link: string) => {
  try {
    const result = await Share.share({
      message: `${name} 친구에게 공유하기\n${link}`,
    });

    // 콘솔 추후 수정 예정
    if (result.action === Share.sharedAction) {
      if (result.activityType) {
        console.log('공유 완료:', result.activityType);
      } else {
        console.log('공유가 완료되었습니다.');
      }
    } else if (result.action === Share.dismissedAction) {
      console.log('공유가 취소되었습니다.');
    }
  } catch (error) {
    console.error('공유 오류:', error);
  }
};

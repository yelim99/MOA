import {StackNavigationProp} from '@react-navigation/stack';

// HomeStack의 ParamList 타입 정의
export type HomeStackParamList = {
  Home: undefined;
  Notification: undefined;
};

// Home 화면에서 사용하는 네비게이션 타입
export type HomeScreenNavigationProp = StackNavigationProp<
  HomeStackParamList,
  'Home'
>;

// MyPageStack의 ParamList 타입 정의
export type MyPageStackParamList = {
  MyPage: undefined;
  // 여기에 스크린 추가
};

// MyPage 화면에서 사용하는 네비게이션 타입
export type MyPageScreenNavigationProp = StackNavigationProp<
  MyPageStackParamList,
  'MyPage'
>;

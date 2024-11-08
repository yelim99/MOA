import {StackNavigationProp} from '@react-navigation/stack';

// HomeStack의 ParamList 타입 정의
export type HomeStackParamList = {
  Home: undefined;
  GroupDetail: {groupInfo: {groupId: string; groupName: string}};
  MomentDetail: {momentInfo: {momentId: string; momentName: string}};
  // 여기에 스크린 추가
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
export type MyPageScreenNavigationProp =
  StackNavigationProp<MyPageStackParamList>;

// App Header의 ParamList 타입 정의
export type AppHeaderParamList = {
  Home: undefined;
  Add: undefined;
  GroupAdd: undefined;
  Notification: undefined;
};

// App Header에서 사용하는 네비게이션 타입
export type AppHeaderNavigationProp = StackNavigationProp<AppHeaderParamList>;

// Stack Header의 ParamList 타입 정의
export type StackHeaderParamList = {
  Notification: undefined;
};

// Stack Header에서 사용하는 네비게이션 타입
export type StackHeaderNavigationProp =
  StackNavigationProp<StackHeaderParamList>;
/* eslint-disable react/no-unstable-nested-components */
import React, {useState, useEffect} from 'react';
import styled, {ThemeProvider} from 'styled-components/native';
import {theme} from './src/styles/theme';
import Navigation from './src/components/common/Navigation';
import {NavigationContainer} from '@react-navigation/native';
import {
  createBottomTabNavigator,
  BottomTabBarProps,
} from '@react-navigation/bottom-tabs';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {createStackNavigator} from '@react-navigation/stack';
import Home from './src/screens/Home';
import MyPage from './src/screens/MyPage';
import Notification from './src/screens/Notification';
import Add from './src/screens/Add';
import GroupAdd from './src/screens/group/GroupAdd';
import GroupDetail from './src/screens/group/GroupDetail';
import MomentAdd from './src/screens/moment/MomentAdd';
import MomentDetail from './src/screens/moment/MomentDetail';
import AppHeader from './src/components/common/header/AppHeader';
import StackHeader from './src/components/common/header/StackHeader';
import {StatusBar} from 'react-native';
import {HomeStackParamList, MyPageStackParamList} from './src/types/screen';
import Login from './src/screens/Login';
import Toast from 'react-native-toast-message';
import {LinkingOptions} from '@react-navigation/native';

const Tab = createBottomTabNavigator();
const RootStack = createStackNavigator();

const CustomTabBar: React.FC<BottomTabBarProps> = (props) => (
  <Navigation {...props} />
);

// 홈 화면(모아) 스택 구조
const HomeStack = createStackNavigator<HomeStackParamList>();

const HomeStackScreen: React.FC = () => (
  <HomeStack.Navigator>
    <HomeStack.Screen
      name="Home"
      component={Home}
      options={({navigation}) => ({
        header: () => <AppHeader navigation={navigation} />,
      })}
    />
    <HomeStack.Screen name="GroupDetail" component={GroupDetail} />
    <HomeStack.Screen name="MomentDetail" component={MomentDetail} />
  </HomeStack.Navigator>
);

// 마이페이지 스택 구조
const MyPageStack = createStackNavigator<MyPageStackParamList>();

const MyPageStackScreen: React.FC = () => (
  <MyPageStack.Navigator>
    <MyPageStack.Screen
      name="MyPage"
      component={MyPage}
      options={({navigation}) => ({
        header: () => <AppHeader navigation={navigation} />,
      })}
    />
  </MyPageStack.Navigator>
);

// Tab Navigator 구성
const TabNavigator: React.FC = () => (
  <Tab.Navigator
    tabBar={CustomTabBar}
    screenOptions={{tabBarHideOnKeyboard: true}}
  >
    <Tab.Screen
      name="HomeStack"
      component={HomeStackScreen}
      options={{headerShown: false}}
    />
    <Tab.Screen
      name="MyPageStack"
      component={MyPageStackScreen}
      options={{headerShown: false}}
    />
  </Tab.Navigator>
);

// 딥링크 설정
const linking: LinkingOptions<HomeStackParamList> = {
  prefixes: ['moa://'],
  config: {
    screens: {
      Home: 'home',
      GroupDetail: 'group/:groupId',
      MomentDetail: 'moment/:momentId',
    },
  },
};

const StyledSafeAreaView = styled.SafeAreaView`
  flex: 1;
`;

const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(true);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkLoginStatus = async () => {
      const token = await AsyncStorage.getItem('jwtToken');
      setIsAuthenticated(!!token);
      setLoading(false);
    };
    checkLoginStatus();
  }, []);

  if (loading) {
    return null; // 로딩 중일 때 빈 화면 또는 로딩 스피너를 보여줄 수 있습니다.
  }

  return (
    <ThemeProvider theme={theme}>
      <StatusBar backgroundColor="#FFFFFF" barStyle="dark-content" />
      <StyledSafeAreaView>
        <NavigationContainer linking={linking}>
          <RootStack.Navigator>
            {isAuthenticated ? (
              <>
                <RootStack.Screen
                  name="Bottom"
                  component={TabNavigator}
                  options={{headerShown: false}}
                />
                <RootStack.Screen
                  name="Add"
                  component={Add}
                  options={() => ({
                    header: () => <StackHeader title="모아 만들기" />,
                    tabBarStyle: {display: 'none'},
                  })}
                />
                <RootStack.Screen
                  name="GroupAdd"
                  component={GroupAdd}
                  options={() => ({
                    header: () => <StackHeader title="그룹 생성" />,
                  })}
                />
                <RootStack.Screen
                  name="MomentAdd"
                  component={MomentAdd}
                  options={() => ({
                    header: () => <StackHeader title="순간 생성" />,
                  })}
                />
                <RootStack.Screen
                  name="Notification"
                  component={Notification}
                  options={{
                    header: () => <StackHeader title="알림" />,
                  }}
                />
              </>
            ) : (
              <RootStack.Screen
                name="Login"
                component={Login}
                options={{headerShown: false}} // 로그인 화면에 헤더 숨기기
              />
            )}
            {/* 여기에 Screen 추가 */}
          </RootStack.Navigator>
        </NavigationContainer>
      </StyledSafeAreaView>
      <Toast />
    </ThemeProvider>
  );
};

export default App;
// export {default} from './.storybook';

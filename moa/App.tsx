/* eslint-disable react/no-unstable-nested-components */
import React from 'react';
import styled, {ThemeProvider} from 'styled-components/native';
import {theme} from './src/styles/theme';
import Navigation from './src/components/common/Navigation';
import {NavigationContainer} from '@react-navigation/native';
import {
  createBottomTabNavigator,
  BottomTabBarProps,
} from '@react-navigation/bottom-tabs';
import {createStackNavigator} from '@react-navigation/stack';
import Home from './src/screens/Home';
import MyPage from './src/screens/MyPage';
import Notification from './src/screens/Notification';
import AppHeader from './src/components/common/header/AppHeader';
import StackHeader from './src/components/common/header/StackHeader';
import {HomeStackParamList, MyPageStackParamList} from './src/types/screen';

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

const StyledSafeAreaView = styled.SafeAreaView`
  flex: 1;
`;

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <StyledSafeAreaView>
        <NavigationContainer>
          <RootStack.Navigator>
            <RootStack.Screen
              name="MainTabs"
              component={TabNavigator}
              options={{headerShown: false}}
            />
            <RootStack.Screen
              name="Notification"
              component={Notification}
              options={{
                header: () => <StackHeader title="알림" />,
              }}
            />
          </RootStack.Navigator>
        </NavigationContainer>
      </StyledSafeAreaView>
    </ThemeProvider>
  );
};

export default App;
// export {default} from './.storybook';

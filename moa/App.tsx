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
import Add from './src/screens/Add';
import GroupAdd from './src/screens/group/GroupAdd';
import GroupDetail from './src/screens/group/GroupDetail';
import MomentAdd from './src/screens/moment/MomentAdd';
import MomentDetail from './src/screens/moment/MomentDetail';
import AppHeader from './src/components/common/header/AppHeader';
import StackHeader from './src/components/common/header/StackHeader';
import {StatusBar} from 'react-native';

const Tab = createBottomTabNavigator();
const RootStack = createStackNavigator();

const CustomTabBar: React.FC<BottomTabBarProps> = (props) => (
  <Navigation {...props} />
);

// Tab Navigator 구성
const TabNavigator: React.FC = () => (
  <Tab.Navigator
    tabBar={CustomTabBar}
    screenOptions={{tabBarHideOnKeyboard: true}}
  >
    <Tab.Screen name="Home" component={Home} options={{headerShown: false}} />
    <Tab.Screen
      name="MyPage"
      component={MyPage}
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
      <StatusBar backgroundColor="#FFFFFF" barStyle="dark-content" />
      <StyledSafeAreaView>
        <NavigationContainer>
          <RootStack.Navigator>
            <RootStack.Screen
              name="Bottom"
              component={TabNavigator}
              options={({navigation}) => ({
                header: () => <AppHeader navigation={navigation} />,
              })}
            />
            <RootStack.Screen
              name="Home"
              component={Home}
              options={({navigation}) => ({
                header: () => <AppHeader navigation={navigation} />,
              })}
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
            <RootStack.Screen
              name="MyPage"
              component={MyPage}
              options={({navigation}) => ({
                header: () => <AppHeader navigation={navigation} />,
              })}
            />
            {/* 여기에 Screen 추가 */}
          </RootStack.Navigator>
        </NavigationContainer>
      </StyledSafeAreaView>
    </ThemeProvider>
  );
};

export default App;
// export {default} from './.storybook';

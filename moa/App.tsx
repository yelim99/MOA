import React from 'react';
import styled, {ThemeProvider} from 'styled-components/native';
import {theme} from './src/styles/theme';
import Navigation from './src/components/common/Navigation';
import {NavigationContainer} from '@react-navigation/native';
import {
  createBottomTabNavigator,
  BottomTabBarProps,
} from '@react-navigation/bottom-tabs';
import Home from './src/screens/Home';
import MyPage from './src/screens/MyPage';

const Tab = createBottomTabNavigator();

const CustomTabBar: React.FC<BottomTabBarProps> = (props) => (
  <Navigation {...props} />
);

const StyledSafeAreaView = styled.SafeAreaView`
  flex: 1;
`;

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <StyledSafeAreaView>
        <NavigationContainer>
          <Tab.Navigator tabBar={CustomTabBar}>
            <Tab.Screen name="Home" component={Home} />
            <Tab.Screen name="MyPage" component={MyPage} />
          </Tab.Navigator>
        </NavigationContainer>
      </StyledSafeAreaView>
    </ThemeProvider>
  );
};

export default App;
// export {default} from './.storybook';

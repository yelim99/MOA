import React from 'react';
import styled, {ThemeProvider} from 'styled-components/native';
import {theme} from './src/styles/theme';
import {SafeAreaView} from 'react-native';
import Navigation from './src/components/common/Navigation';
import {NavigationContainer} from '@react-navigation/native';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import Home from './src/screens/Home';
import MyPage from './src/screens/MyPage';
import {GlobalStyles} from './src/styles/GlobalStyles';

const Tab = createBottomTabNavigator();

const StyledSafeAreaView = styled.SafeAreaView`
  ${GlobalStyles};
`;

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <StyledSafeAreaView style={{flex: 1}}>
        <NavigationContainer>
          <Tab.Navigator tabBar={(props) => <Navigation {...props} />}>
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

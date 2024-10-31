import React from 'react';
import {ThemeProvider} from 'styled-components/native';
import {theme} from './src/styles/theme';
import {SafeAreaView, Text, View} from 'react-native';
import {GlobalStyles} from './src/styles/GlobalStyles';
import styled from 'styled-components/native';
import Test from './src/components/Test';
import Navigation from './src/components/Navigation';
import {NavigationContainer} from '@react-navigation/native';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import Home from './src/screens/Home';
import MyPage from './src/screens/MyPage';

const Container = styled.View`
  background-color: ${({theme}) => theme.colors.lightblue};
  padding: 10px;
`;
const StyledText = styled.Text`
  ${GlobalStyles};
`;

const Tab = createBottomTabNavigator();

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <SafeAreaView style={{flex: 1}}>
        <NavigationContainer>
          <Tab.Navigator tabBar={(props) => <Navigation {...props} />}>
            <Tab.Screen name="Home" component={Home} />
            <Tab.Screen name="MyPage" component={MyPage} />
          </Tab.Navigator>
        </NavigationContainer>
      </SafeAreaView>
    </ThemeProvider>
  );
};

export default App;
// export {default} from './.storybook';

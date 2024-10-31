// /**
//  * Sample React Native App
//  * https://github.com/facebook/react-native
//  *
//  * @format
//  */

// import React from 'react';
// import {BottomSheetModalProvider} from '@gorhom/bottom-sheet';
// import type {PropsWithChildren} from 'react';
// import {
//   SafeAreaView,
//   ScrollView,
//   StatusBar,
//   StyleSheet,
//   Text,
//   useColorScheme,
//   View,
// } from 'react-native';

// import {
//   Colors,
//   DebugInstructions,
//   Header,
//   LearnMoreLinks,
//   ReloadInstructions,
// } from 'react-native/Libraries/NewAppScreen';

// type SectionProps = PropsWithChildren<{
//   title: string;
// }>;

// function Section({children, title}: SectionProps): React.JSX.Element {
//   const isDarkMode = useColorScheme() === 'dark';
//   return (
//     <BottomSheetModalProvider>
//       <View style={styles.sectionContainer}>
//         <Text
//           style={[
//             styles.sectionTitle,
//             {
//               color: isDarkMode ? Colors.white : Colors.black,
//             },
//           ]}
//         >
//           {title}
//         </Text>
//         <Text
//           style={[
//             styles.sectionDescription,
//             {
//               color: isDarkMode ? Colors.light : Colors.dark,
//             },
//           ]}
//         >
//           {children}
//         </Text>
//       </View>
//     </BottomSheetModalProvider>
//   );
// }

// function App(): React.JSX.Element {
//   const isDarkMode = useColorScheme() === 'dark';

//   const backgroundStyle = {
//     backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
//   };

//   return (
//     <SafeAreaView style={backgroundStyle}>
//       <StatusBar
//         barStyle={isDarkMode ? 'light-content' : 'dark-content'}
//         backgroundColor={backgroundStyle.backgroundColor}
//       />
//       <ScrollView
//         contentInsetAdjustmentBehavior="automatic"
//         style={backgroundStyle}
//       >
//         <Header />
//         <View
//           style={{
//             backgroundColor: isDarkMode ? Colors.black : Colors.white,
//           }}
//         >
//           <Section title="Step One">
//             Edit <Text style={styles.highlight}>App.tsx</Text> to change this
//             screen and then come back to see your edits.
//           </Section>
//           <Section title="See Your Changes">
//             <ReloadInstructions />
//           </Section>
//           <Section title="Debug">
//             <DebugInstructions />
//           </Section>
//           <Section title="Learn More">
//             Read the docs to discover what to do next:
//           </Section>
//           <LearnMoreLinks />
//         </View>
//       </ScrollView>
//     </SafeAreaView>
//   );
// }

// const styles = StyleSheet.create({
//   sectionContainer: {
//     marginTop: 32,
//     paddingHorizontal: 24,
//   },
//   sectionTitle: {
//     fontSize: 24,
//     fontWeight: '600',
//   },
//   sectionDescription: {
//     marginTop: 8,
//     fontSize: 18,
//     fontWeight: '400',
//   },
//   highlight: {
//     fontWeight: '700',
//   },
// });

// // export default App;
// export {default} from './.storybook';
import React from 'react';
import {ThemeProvider} from 'styled-components/native';
import {theme} from './src/styles/theme';
import {SafeAreaView, Text, View} from 'react-native';
import {GlobalStyles} from './src/styles/GlobalStyles';
import styled from 'styled-components/native';
import Test from './src/components/Test';

const Container = styled.View`
  background-color: ${({theme}) => theme.colors.lightblue};
  padding: 10px;
`;
const StyledText = styled.Text`
  ${GlobalStyles};
`;
const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <SafeAreaView>
        <Container>
          <View>
            <Test></Test>
            <StyledText>
              글씨 크기랑 색은 Container컴포넌트를 통해 적용되고, 텍스트 관련은
              GlobalStyles로 적용됨!!
            </StyledText>
          </View>
        </Container>
      </SafeAreaView>
    </ThemeProvider>
  );
};

export default App;
// export {default} from './.storybook';

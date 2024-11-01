module.exports = {
  presets: ['module:@react-native/babel-preset'],
  // babel.config.js
  plugins: [
    'react-native-reanimated/plugin',
    [
      'module:react-native-dotenv',
      {
        moduleName: '@env',
        path: '.env',
      },
    ],
  ],
};

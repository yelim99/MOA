// src/components/common/button/ButtonDemo.tsx
import React from 'react';
import {View} from 'react-native';
import Button from './Button';
import Icon from 'react-native-vector-icons/MaterialIcons';

const ButtonTest = () => {
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      {/* 텍스트 버튼 예시 */}
      <Button
        text="그룹 만들기"
        content="text"
        size="large"
        backcolor="maindarkorange"
        onPress={() => console.log('그룹 만들기 버튼 눌림')}
      />

      <Button
        text="다운로드"
        content="text"
        size="small"
        backcolor="white"
        onPress={() => console.log('다운로드 버튼 눌림')}
      />

      {/* 아이콘만 있는 버튼 예시 */}
      <Button
        icon={<Icon name="edit" size={16} color="maindarkorange" />}
        content="icon"
        backcolor="white"
        onPress={() => console.log('아이콘 버튼 눌림')}
      />

      {/* 색상 선택 버튼 예시 */}
      <Button
        content="colorSelect"
        size="small"
        backcolor="#FAE5E3" // lightred
        onPress={() => console.log('색상 선택 버튼 눌림')}
      />
    </View>
  );
};

export default ButtonTest;

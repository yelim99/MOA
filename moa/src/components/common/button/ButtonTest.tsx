// src/components/common/button/ButtonDemo.tsx
import React from 'react';
import {View} from 'react-native';
import Button from './Button';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {IconButton} from './IconButton';
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

      {/* 아이콘 버튼 예시  */}
      <IconButton
        backcolor="white"
        iconName="edit"
        iconSet="Material"
        onPress={() => console.log('편집 아이콘 버튼 눌렀음')}
      ></IconButton>
      <IconButton
        backcolor="maindarkorange"
        iconSet="Feather"
        iconName="external-link"
        onPress={() => console.log('공유하기 아이콘 버튼 눌렀음')}
      />
      <IconButton
        backcolor="white"
        iconSet="Feather"
        iconName="external-link"
        onPress={() => console.log('공유하기 아이콘 버튼 눌렀음')}
      />
      <IconButton
        backcolor="white"
        iconSet="Feather"
        iconName="more-vertical"
        onPress={() => console.log('점점점 아이콘 ')}
      ></IconButton>
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

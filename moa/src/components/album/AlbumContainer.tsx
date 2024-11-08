import React, {useState} from 'react';
import styled, {useTheme} from 'styled-components/native';
import PhotoList from './PhotoList';
import Icon from 'react-native-vector-icons/MaterialIcons';
import StyledModal from '../common/modal/StyledModal';
import RNFS from 'react-native-fs';
import {Alert, Linking, PermissionsAndroid, Platform} from 'react-native';
import {
  request,
  requestMultiple,
  PERMISSIONS,
  RESULTS,
} from 'react-native-permissions';

const Container = styled.View`
  width: 100%;
  padding-bottom: 100px;
`;

const TitleLine = styled.View`
  flex-direction: row;
  align-items: center;
  margin-bottom: 15px;
`;

const Title = styled.Text`
  font-family: SCDream5;
  font-size: 15px;
  margin-right: 10px;
`;

const TitleNum = styled(Title)<{darkColor: string}>`
  color: ${({darkColor}) => darkColor};
`;

const ButtonLine = styled.View`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  align-items: center;
`;

const OptionButton = styled.TouchableOpacity`
  width: 130px;
  height: 30px;
  border: 1px solid ${({theme}) => theme.colors.deepgray};
  border-radius: 10px;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
  margin-top: 5px;
  margin-bottom: 10px;
`;

const OptionButtonText = styled.Text`
  font-family: SCDream4;
  font-size: 13px;
`;

const DownloadButton = styled.TouchableOpacity<{
  lightColor: string;
}>`
  width: 90px;
  height: 30px;
  border-radius: 10px;
  background-color: ${({lightColor}) => lightColor};
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
`;

const DownloadButtonText = styled.Text<{
  darkColor: string;
}>`
  font-family: SCDream5;
  font-size: 13px;
  color: ${({darkColor}) => darkColor};
`;

const ModalItemContainer = styled.TouchableOpacity`
  width: 100%;
  padding: 15px;
  align-items: center;
  justify-content: center;
`;

const ModalItem = styled.Text`
  font-family: SCDream4;
  font-size: 16px;
`;

interface AlbumContainerProps {
  title: string;
  lightColor: string;
  darkColor: string;
}

// const requestStoragePermission = async () => {
//   let permissionType;
//   if (Platform.OS === 'android') {
//     permissionType = PERMISSIONS.ANDROID.WRITE_EXTERNAL_STORAGE;
//   } else {
//     return true;
//   }

//   const result = await request(permissionType);
//   if (result === RESULTS.GRANTED) {
//     return true;
//   } else {
//     Alert.alert('권한 필요', '사진을 저장하려면 저장 권한이 필요합니다.');
//     return false;
//   }
// };

const AlbumContainer = ({
  title,
  lightColor,
  darkColor,
}: AlbumContainerProps) => {
  const theme = useTheme();

  const [selectedPhotos, setSelectedPhotos] = useState<string[]>([]);

  const options = [
    {id: 'mine', label: '내가 나온 사진'},
    {id: 'scenary', label: '풍경'},
    {id: 'food', label: '음식'},
  ];

  const [isOptionModalVisible, setIsOptionModalVisible] = useState(false);

  const toggleOptionModal = () => {
    setIsOptionModalVisible(!isOptionModalVisible);
  };

  const handleSelectOption = (optionId: string) => {
    toggleOptionModal();

    if (optionId === 'mine') {
      toggleOptionModal();
    } else if (optionId === 'scenary') {
      toggleOptionModal();
    } else if (optionId === 'food') {
      toggleOptionModal();
    }
  };

  // 접근 권한 요청
  const requestStoragePermission = async () => {
    try {
      if (Platform.OS === 'android') {
        if (Platform.Version >= 30) {
          // Android 11 이상: MANAGE_EXTERNAL_STORAGE 권한 필요 -> WRITE여도 되는데 나중에 배포 후 재테스트
          const granted = await PermissionsAndroid.request(
            PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
            {
              title: 'External Storage Permission',
              message:
                'This app needs access to your storage to download photos.',
              buttonPositive: 'Allow',
            },
          );

          if (granted !== PermissionsAndroid.RESULTS.GRANTED) {
            Alert.alert(
              '권한 설정 필요',
              '갤러리 저장 권한이 필요합니다. 설정에서 권한을 활성화해주세요.',
              [
                {
                  text: '설정으로 이동',
                  onPress: () => Linking.openSettings(),
                },
                {
                  text: '취소',
                  style: 'cancel',
                },
              ],
            );
            return false;
          }

          return true;
        } else {
          // Android 10 이하: WRITE_EXTERNAL_STORAGE 권한 요청
          const granted = await PermissionsAndroid.request(
            PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
            {
              title: 'External Storage Permission',
              message:
                'This app needs access to your storage to download photos.',
              buttonPositive: 'Allow',
            },
          );
          return granted === PermissionsAndroid.RESULTS.GRANTED;
        }
      }
      return true;
    } catch (err) {
      console.warn(err);
      return false;
    }
  };

  // 사진 다운로드
  const handleDownload = async () => {
    const hasPermission = await requestStoragePermission();
    if (!hasPermission) {
      Alert.alert('권한 필요', '사진을 저장하려면 저장 권한이 필요합니다.');
      return;
    }

    if (selectedPhotos.length === 0) {
      Alert.alert('선택된 사진이 없습니다.');
      return;
    }
    try {
      const picturesDir = RNFS.PicturesDirectoryPath;
      const dirExists = await RNFS.exists(picturesDir);
      if (!dirExists) {
        await RNFS.mkdir(picturesDir);
      }

      await Promise.all(
        selectedPhotos.map(async (uri, index) => {
          const fileName = `downloaded_image_${index + 5}.jpg`;
          const downloadDest = `${RNFS.PicturesDirectoryPath}/${fileName}`;

          const downloadResult = await RNFS.downloadFile({
            fromUrl: uri,
            toFile: downloadDest,
          }).promise;

          if (downloadResult.statusCode === 200) {
            await RNFS.scanFile(downloadDest);
          } else {
            throw new Error('다운로드 실패');
          }
        }),
      );

      Alert.alert(
        '다운로드 완료',
        '선택된 이미지들이 갤러리에 저장되었습니다.',
      );
    } catch (error) {
      console.error('Download Error:', error);
      Alert.alert('다운로드 오류', '다운로드 중 문제가 발생했습니다.');
    }
  };

  return (
    <Container>
      <TitleLine>
        <Title>{title}</Title>
        <TitleNum darkColor={darkColor}>255장</TitleNum>
      </TitleLine>
      <ButtonLine>
        <OptionButton onPress={toggleOptionModal}>
          <Icon name="monochrome-photos" size={15} color={theme.colors.black} />
          <OptionButtonText>사진 분류 옵션</OptionButtonText>
        </OptionButton>
        <DownloadButton lightColor={lightColor} onPress={handleDownload}>
          <Icon name="download" size={15} color={darkColor} />
          <DownloadButtonText darkColor={darkColor}>
            다운로드
          </DownloadButtonText>
        </DownloadButton>
      </ButtonLine>
      <StyledModal
        isModalVisible={isOptionModalVisible}
        toggleModal={toggleOptionModal}
      >
        {options.map((option) => (
          <ModalItemContainer
            key={option.id}
            onPress={() => handleSelectOption(option.id)}
          >
            <ModalItem>{option.label}</ModalItem>
          </ModalItemContainer>
        ))}
      </StyledModal>
      <PhotoList onSelectionChange={setSelectedPhotos} />
    </Container>
  );
};

export default AlbumContainer;

import React, {useState} from 'react';
import {
  View,
  Modal,
  Text,
  TouchableOpacity,
  StyleSheet,
  Button,
} from 'react-native';
import {launchCamera, launchImageLibrary} from 'react-native-image-picker';
import {TextButton} from '../common/button/TextButton';

const MyComponent = () => {
  const [modalVisible, setModalVisible] = useState(false);

  const handleSelectOption = (option: string) => {
    setModalVisible(false);
    if (option === 'camera') {
      launchCamera({mediaType: 'photo'}, (response) => {
        if (response.didCancel) {
          console.log('User cancelled image picker');
        } else if (response.errorMessage) {
          console.error('ImagePicker Error: ', response.errorMessage);
        } else {
          console.log('Photo taken:', response.assets?.[0].uri);
        }
      });
    } else if (option === 'gallery') {
      launchImageLibrary({mediaType: 'photo'}, (response) => {
        if (response.didCancel) {
          console.log('User cancelled image picker');
        } else if (response.errorMessage) {
          console.error('ImagePicker Error: ', response.errorMessage);
        } else {
          console.log('Photo selected:', response.assets?.[0].uri);
        }
      });
    }
  };

  return (
    <View>
      <TextButton
        backcolor="mainlightorange"
        text="등록하기"
        iconName="plus"
        iconSet="Feather"
        size="small"
        onPress={() => setModalVisible(true)}
      />
      <Modal
        transparent={true}
        animationType="slide"
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.modalBackground}>
          <View style={styles.modalContainer}>
            <TouchableOpacity
              onPress={() => handleSelectOption('camera')}
              style={styles.optionButton}
            >
              <Text style={styles.optionText}>사진 촬영</Text>
            </TouchableOpacity>
            <TouchableOpacity
              onPress={() => handleSelectOption('gallery')}
              style={styles.optionButton}
            >
              <Text style={styles.optionText}>갤러리에서 선택</Text>
            </TouchableOpacity>
            <TouchableOpacity
              onPress={() => setModalVisible(false)}
              style={styles.cancelButton}
            >
              <Text style={styles.cancelText}>취소</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  modalBackground: {
    flex: 1,
    justifyContent: 'flex-end',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalContainer: {
    backgroundColor: 'white',
    padding: 20,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  optionButton: {
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  optionText: {
    fontSize: 18,
  },
  cancelButton: {
    padding: 15,
    alignItems: 'center',
  },
  cancelText: {
    fontSize: 18,
    color: 'red',
  },
});

export default MyComponent;

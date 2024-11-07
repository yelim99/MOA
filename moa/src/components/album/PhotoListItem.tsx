import React from 'react';
import styled, {useTheme} from 'styled-components/native';
import Icon from 'react-native-vector-icons/FontAwesome';

const Container = styled.TouchableOpacity<{itemSize: number}>`
  width: ${({itemSize}) => itemSize}px;
  height: ${({itemSize}) => itemSize}px;
  margin-bottom: 5px;
`;

const StyledImage = styled.Image<{itemSize: number}>`
  width: ${({itemSize}) => itemSize}px;
  height: ${({itemSize}) => itemSize}px;
`;

const CheckButton = styled.View`
  position: absolute;
  top: 5px;
  right: 5px;
  width: 15px;
  height: 15px;
  border-radius: 100%;
  border: 1px solid ${({theme}) => theme.colors.mediumgray};
  background-color: ${({theme}) => theme.colors.lightgray};
  justify-content: center;
  align-items: center;
`;

interface PhotoListItemProps {
  uri: string;
  isSelected: boolean;
  onToggleSelect: () => void;
  itemSize: number;
}

const PhotoListItem = ({
  uri,
  isSelected,
  onToggleSelect,
  itemSize,
}: PhotoListItemProps) => {
  const theme = useTheme();

  return (
    <Container itemSize={itemSize} onPress={onToggleSelect}>
      <StyledImage
        itemSize={itemSize}
        source={{
          uri,
        }}
        resizeMode="cover"
      />
      <CheckButton>
        {isSelected && (
          <Icon name="check" size={10} color={theme.colors.deepgray} />
        )}
      </CheckButton>
    </Container>
  );
};

export default PhotoListItem;

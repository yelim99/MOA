import React from 'react';
import styled, {useTheme} from 'styled-components/native';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import FAIcon from 'react-native-vector-icons/FontAwesome6';

const Container = styled.View<{bgColor: string}>`
  width: 30px;
  height: 30px;
  border-radius: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${({bgColor}) => bgColor};
`;

interface GroupIconButtonProps {
  color: string;
  iconName: string;
}

const GroupIconButton = ({color, iconName}: GroupIconButtonProps) => {
  const theme = useTheme();

  const colorMap: {[key: string]: string} = {
    red: theme.colors.darkred,
    yellow: theme.colors.darkyellow,
    green: theme.colors.darkgreen,
    blue: theme.colors.darkblue,
    purple: theme.colors.darkpurple,
    pink: theme.colors.darkpink,
  };

  const renderIcon = () => {
    if (
      iconName === 'chat' ||
      iconName === 'briefcase' ||
      iconName === 'home'
    ) {
      return (
        <EntypoIcon name={iconName} size={17} color={theme.colors.white} />
      );
    } else if (iconName === 'heart') {
      return (
        <EntypoIcon name={iconName} size={19} color={theme.colors.white} />
      );
    } else if (iconName === 'book-open' || iconName === 'graduation-cap') {
      return <FAIcon name={iconName} size={15} color={theme.colors.white} />;
    }
    return null;
  };

  return <Container bgColor={colorMap[color]}>{renderIcon()}</Container>;
};

export default GroupIconButton;

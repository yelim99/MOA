import React, {useState} from 'react';
import styled from 'styled-components/native';
import PhotoListItem from './PhotoListItem';

const Container = styled.View`
  width: 100%;
  padding: 10px 0;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
`;

const PhotoList = () => {
  const [containerWidth, setContainerWidth] = useState(0);
  const [selectedPhotos, setSelectedPhotos] = useState<string[]>([]);

  const itemSize = (containerWidth - 3 * 5) / 4;

  // 테스트용 데이터 -> 나중에 삭제
  const photoList = [
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/09500345-a3e3-4659-a1dc-a562a135a2db.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/37685845-5d55-4e13-95ec-ab8ace065c01.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/9a50a553-1114-4430-8cdc-615b4281b01d.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/09500345-a3e3-4659-a1dc-a562a135a2db.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/37685845-5d55-4e13-95ec-ab8ace065c01.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/9a50a553-1114-4430-8cdc-615b4281b01d.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/09500345-a3e3-4659-a1dc-a562a135a2db.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/37685845-5d55-4e13-95ec-ab8ace065c01.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/9a50a553-1114-4430-8cdc-615b4281b01d.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/09500345-a3e3-4659-a1dc-a562a135a2db.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/37685845-5d55-4e13-95ec-ab8ace065c01.jfif',
    },
    {
      uri: 'https://moa-s3-bucket.s3.ap-northeast-2.amazonaws.com/group/200/moment/672c22722a98f4294e24fe0e/9a50a553-1114-4430-8cdc-615b4281b01d.jfif',
    },
  ];

  const toggleSelect = (uri: string) => {
    setSelectedPhotos((prev) =>
      prev.includes(uri)
        ? prev.filter((photo) => photo !== uri)
        : [...prev, uri],
    );
  };

  return (
    <Container
      onLayout={(event) => {
        const {width} = event.nativeEvent.layout;
        setContainerWidth(width);
      }}
    >
      {photoList.map((photo) => (
        <PhotoListItem
          key={photo.uri}
          uri={photo.uri}
          isSelected={selectedPhotos.includes(photo.uri)}
          onToggleSelect={() => toggleSelect(photo.uri)}
          itemSize={itemSize}
        />
      ))}
    </Container>
  );
};

export default PhotoList;

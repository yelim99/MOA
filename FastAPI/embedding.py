import face_recognition

def get_face_embedding(image):
    """
    입력된 얼굴 이미지에서 임베딩 값을 추출합니다.
    """
    face_locations = face_recognition.face_locations(image)
    if not face_locations:
        raise ValueError("얼굴을 찾을 수 없습니다.")
    
    # 얼굴 인코딩(임베딩) 추출
    face_encoding = face_recognition.face_encodings(image, face_locations)[0]
    return face_encoding

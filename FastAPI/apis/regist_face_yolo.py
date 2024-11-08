from fastapi import FastAPI, HTTPException, Response
from embedding import get_face_embedding
from pydantic import BaseModel
from ultralytics import YOLO
import requests
import face_recognition
import numpy as np
import cv2
import io

app = FastAPI()

# 얼굴 검출 YOLO 모델 로드
detection_model = YOLO('../model/face-detection.pt')

class ImageRequest(BaseModel):
    image_url: str

"""
# 얼굴 이미지 업로드를 통해 임베딩 값을 추출하고 등록하는 엔드포인트
# 백에서 S3에 저장된 이미지 URL 받아와서 다운 후 임베딩 값 추출
# 얼굴 검출은 yolo, 임베딩 값 추출은 opencv 라이브러리 활용
# 추출된 임베딩 값을 다시 백으로 전달
"""
@app.post("/fast/register_face/")
async def register_face(request: ImageRequest):
    try:
        image_url = request.image_url
        print(f"Fetching image from URL: {image_url}")

        # 이미지 URL에서 이미지 다운로드
        response = requests.get(image_url)
        if response.status_code != 200:
            print(f"Failed to fetch image URL: {image_url}")
            raise HTTPException(status_code=400, detail="Image not found")

        # 이미지 파일을 읽고 OpenCV 형식으로 변환
        np_img = np.frombuffer(response.content, np.uint8)
        image = cv2.imdecode(np_img, cv2.IMREAD_COLOR)

        # YOLO로 얼굴 검출
        print("Detecting face using YOLO")
        yolo_results = detection_model(image)
        face_locations = []

        for result in yolo_results:
            x, y, w, h = result  # YOLO 바운딩 박스 좌표
            top = y
            right = x + w
            bottom = y + h
            left = x
            face_locations.append((top, right, bottom, left))

        if not face_locations:
            raise ValueError("얼굴을 찾을 수 없습니다.")

        # 얼굴 인코딩(임베딩) 추출
        face_embedding = face_recognition.face_encodings(image, face_locations)[0]
        
        # 추출한 임베딩 값을 바이너리 데이터로 변환
        print("Converting face embedding to bytes")
        face_embedding_bytes = np.array(face_embedding, dtype=np.float32).tobytes()

        # 바이너리 데이터 반환
        # application/octet-stream은 8비트 단위의 바이너리 데이터로,
        # 특별히 표현할 수 있는 타입이 없는 경우 사용
        print("Returning face_embedding_byte")
        return Response(content = face_embedding_bytes, media_type="application/octet-stream")

    except Exception as e:
        print(f"Error occurred: {str(e)}")
        raise HTTPException(status_code=500, detail=str(e))



# 서버 실행
# if __name__ == "__main__":
#     import uvicorn
#     uvicorn.run(app, host="0.0.0.0", port=8000)
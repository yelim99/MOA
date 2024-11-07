from fastapi import FastAPI, UploadFile, File, HTTPException
from embedding import get_face_embedding
import face_recognition
import numpy as np
import cv2
import io

app = FastAPI()


"""
# 얼굴 이미지 업로드를 통해 임베딩 값을 추출하고 등록하는 엔드포인트
# name은 얼굴과 함께 등록할 이름, file은 업로드한 이미지 파일
# 클라이언트에서 사진 파일과 JWT 토큰을 같이 넘겨줘야 함.
# 받은 사진에서 임베딩 값 추출 후, Spring에 post 요청으로 JWT 토큰, 사진 파일, 임베딩 값을 넘겨줌.
# Spring에서는 받아서 JWT 토큰으로 유저 확인 후 임베딩 값과 사진을 각각 DB에 저장
# FAST로 응답 보내면 클라이언트로 응답 전달
"""
@app.post("/fast/register_face/")
async def register_face(
    file: UploadFile = File(...), 
    authorization: str = Header(None)
):
    # JWT 토큰 확인
    if authorization is None:
        raise HTTPException(status_code=401, detail="JWT token is missing")

    # 이미지 파일을 읽고 OpenCV 형식으로 변환
    image_data = await file.read()
    np_img = np.frombuffer(image_data, np.uint8)
    image = cv2.imdecode(np_img, cv2.IMREAD_COLOR)

    try:
        face_embedding = get_face_embedding(image)
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

    # Spring으로 전송 준비 (임베딩 값을 바이트 형식으로 변환)
    face_embedding_bytes = np.array(face_embedding, dtype=np.float32).tobytes()

    # Spring 서버로 요청 보내기
    spring_url = "http://k11a602.p.ssafy.io/api/receive-embedding"
    headers = {
        "Authorization": authorization  # JWT 토큰
    }
    files = {
        "image": (file.filename, image_data, "image/jpeg"),
        "embedding": ("embedding", face_embedding_bytes, "application/octet-stream")
    }
    
    spring_response = requests.post(spring_url, headers=headers, files=files)
    if spring_response.status_code != 200:
        raise HTTPException(status_code=spring_response.status_code, detail="Failed to send to Spring")
    # return {"message": "Embedding and image sent successfully to Spring"}

    # Spring 서버에서 받은 응답을 그대로 클라이언트로 전달
    return Response(
        content=spring_response.content, 
        status_code=spring_response.status_code, 
        media_type=spring_response.headers.get("Content-Type")
    )


# 서버 실행
# if __name__ == "__main__":
#     import uvicorn
#     uvicorn.run(app, host="0.0.0.0", port=8000)
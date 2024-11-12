from fastapi import FastAPI, UploadFile, File, HTTPException
from pydantic import BaseModel
from fastapi import APIRouter
from fastapi import HTTPException, Response  # Response 추가
import requests  # requests 라이브러리 추가
import logging
import face_recognition
import numpy as np
import cv2
import io
import os
import boto3

router = APIRouter()

# 기본 설정
ROOT = os.path.dirname(__file__)
logging.basicConfig(level=logging.INFO)
pcs = set()


# S3 클라이언트 설정
s3 = boto3.client('s3', region_name='ap-northeast-2')
bucket_name = 'moa-s3-bucket'

# 요청 데이터 모델 정의
class FaceCompareRequest(BaseModel):
    group_id: int
    moment_ids: list
    reference_embedding: list

@router.post("/")
async def compare_face(request: FaceCompareRequest):
    group_id = request.group_id
    moment_ids = request.moment_ids
    reference_embedding_array = np.array(request.reference_embedding, dtype=np.float32)

    # S3`` 프리픽스 설정
    prefix = f"group/{group_id}/moment/{moment_id}/"  

    matching_urls = []

    # S3에서 특정 경로의 모든 파일 가져오기
    try:
         # 각 moment_id에 대해 S3 경로를 확인하고 처리
        for moment_id in moment_ids:
            prefix = f"group/{group_id}/moment/{moment_id}/"
            response = s3.list_objects_v2(Bucket=bucket_name, Prefix=prefix)

            for obj in response.get('Contents', []):
                file_key = obj['Key']
                file_url = f"https://{bucket_name}.s3.ap-northeast-2.amazonaws.com/{file_key}"

                # S3에서 이미지 다운로드
                s3_object = s3.get_object(Bucket=bucket_name, Key=file_key)
                image_data = s3_object['Body'].read()

                # OpenCV로 이미지 디코딩 및 얼굴 임베딩 추출
                np_img = np.frombuffer(image_data, np.uint8)
                image = cv2.imdecode(np_img, cv2.IMREAD_COLOR)
                face_locations = face_recognition.face_locations(image)
                face_encodings = face_recognition.face_encodings(image, face_locations)

                # 얼굴 비교
                for encoding in face_encodings:
                    match = face_recognition.compare_faces([reference_embedding_array], encoding, tolerance=0.45)
                    if match[0]:
                        matching_urls.append(file_url)
                        break  # 일치하는 얼굴이 발견되면 다음 이미지로

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

    # Spring으로 비교 결과 반환
    return {"matching_urls": matching_urls}

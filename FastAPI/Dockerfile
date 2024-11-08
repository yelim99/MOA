# 베이스 이미지
FROM python:3.7.16-slim

# 작업 디렉토리 설정
# 우분투에서 docker exec 하면 처음 들어가지는 폴더 이름
WORKDIR /app

# 시스템 패키지 업데이트 및 캐시 정리
RUN apt-get update && \
    apt-get install -y --no-install-recommends cmake make g++ libgl1 && \
    rm -rf /var/lib/apt/lists/* 

# 의존성을 먼저 설치하여 Docker 레이어 캐시 활용
COPY requirements.txt .

RUN --mount=type=cache,target=/root/.cache/pip \
    pip install -r requirements.txt --no-cache-dir && \
    pip install dlib

# 소스 코드 복사
COPY . .

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8008"]

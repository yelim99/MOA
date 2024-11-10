# CUDA 11.4 및 cuDNN 포함된 NVIDIA 이미지 사용
FROM nvidia/cuda:11.4.2-cudnn8-runtime-ubuntu20.04

# 작업 디렉토리 설정
WORKDIR /app

# 시스템 패키지 업데이트 및 필요한 패키지 설치
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    python3.7 python3-pip cmake make g++ libgl1 libglib2.0-0 && \
    rm -rf /var/lib/apt/lists/*

# Python3.7을 기본 python으로 설정
RUN update-alternatives --install /usr/bin/python python /usr/bin/python3.7 1 && \
    update-alternatives --install /usr/bin/pip pip /usr/bin/pip3 1

# 의존성 설치
COPY requirements.txt .

RUN --mount=type=cache,target=/root/.cache/pip \
    pip install -r requirements.txt --no-cache-dir

# dlib을 CUDA 지원으로 설치
RUN pip install dlib --no-cache-dir --global-option=build_ext --global-option="-DUSE_AVX_INSTRUCTIONS=1" --global-option="-DUSE_CUDA=1"

# 소스 코드 복사
COPY . .

# FastAPI 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8008"]

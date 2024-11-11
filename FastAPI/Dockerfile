# CUDA 11.4 및 cuDNN 포함된 NVIDIA 이미지 사용
FROM nvidia/cuda:11.8.0-cudnn8-devel-ubuntu20.04

# 작업 디렉토리 
WORKDIR /app

# 비대화 모드 설정
ENV DEBIAN_FRONTEND=noninteraction

# 시스템 패키지 업데이트 및 필요한 패키지 설치
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    software-properties-common git tzdata && \
    ln -fs /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata && \
    add-apt-repository ppa:deadsnakes/ppa && \
    apt-get update && \
    apt-get install -y --no-install-recommends \    
    python3.7 python3.7-dev python3.7-distutils python3-pip cmake make g++ \
    libgl1 libglib2.0-0 libx11-dev libopenblas-dev liblapack-dev && \
    rm -rf /var/lib/apt/lists/*

# Python3.7을 기본 python으로 설정
RUN ln -sf /usr/bin/python3.7 /usr/bin/python && \
    ln -sf /usr/bin/pip3 /usr/bin/pip

# 의존성 설치
COPY requirements.txt .

RUN --mount=type=cache,target=/tmp/.cache/pip \
    pip install -r requirements.txt --no-cache-dir

# CUDA 지원을 위한 dlib 소스 설치
RUN git clone https://github.com/davisking/dlib.git && \
    mkdir -p dlib/build && cd dlib/build && \
    cmake .. -DDLIB_USE_CUDA=1 -DUSE_AVX_INSTRUCTIONS=1 -DCUDA_TOOLKIT_ROOT_DIR=/usr/local/cuda && \
    cmake --build . && \
    cd ../ && python setup.py install --set DLIB_USE_CUDA=1

RUN pip install face_recognition==1.3.0 face_recognition_models==0.3.0 --no-cache-dir

# 소스 코드 복사
COPY . .

# FastAPI 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8008"]

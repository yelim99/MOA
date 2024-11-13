from fastapi import APIRouter
from apis import regist_face
from apis import compare_face

# 라우터를 연결해주는 역할
api_router = APIRouter()
api_router.include_router(regist_face.router,prefix="/fast/regist_face", tags=["regist_face"])
api_router.include_router(compare_face.router,prefix="/fast/compare_face", tags=["compare_face"])

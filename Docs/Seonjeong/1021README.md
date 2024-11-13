# 기획 주제

- 사진 공유 및 자동 본류 서비스

## 고객 문제

- 사진이 너무 많이 올라와서 개인 사진을 찾기 어려웠어요.
- 내 사진만 다운받고 싶은데, 일일이 확인하기엔 귀찮고, 사진을 모두 다운받기에는 핸드폰 용량이 없어요.
- 페스티벌에 참여를 했는데, 주최 측에서 잘 나온 사진만 올려줘서 내 사진이 없어요.

```
   ⇒ 페스티벌과 같은 일시적인 상황에서도 내가 나온 사진의 공유가 원활하게 되었으면 좋겠어요.

   ⇒ 행사에서 주최 측이 선별된 사진만 공개하면, 참가자들이 자신의 사진을 받지 못해 아쉬움을 느낄 수 있습니다. (GPT 버전)

   ⇒ 사진이 선별적으로 공유되면, 참여자들이 자신의 사진을 확인하지 못해 아쉬움을 느낄 수 있습니다. (GPT 버전)
```

- 유치원, 어린이집 교사의 경우 아동의 사진을 하나하나 분류해서 나눠줘야해서 번거로워요. 그렇다고 알림장처럼 본격적인 플랫폼을 사용하기에는 부담스러워요. 딱 사진만 공유할 수 있었으면 좋겠어요.

## 솔루션

- 사진을 분류할 때 옵션을 선택할 수 있도록 (인물, 음식, 풍경 ..)
- 클라우드 환경에서 내가 원하는 사진만 다운받을 수 있다..

## 고유 가치 제안(차별점)

- 모든 사진을 다 다운받을 필요가 없다. → 다운받으면서 다른 작업 가능
- 그룹의 사진을 간편하게 분류해서 공유(업로드, 다운) 할 수 있다.
- 고정방뿐만 아니라 임시방에서도 간편하게 사진 공유 및 분류 가능
- 다른 부가기능보다는 메인 기능에 집중
  - SNS적인 성격을 가지면 부담스러워하는 사람들이 많음

## 핵심 기능 (고유 가치 제안의 구체화)

1. 일회성 사진 공유
2. 사진 분류

## 부가 기능

- 알림
  - 내가 있는 방에 누군가 사진을 업로드하면 알림
  - 방 터지기 전 알림
  - 사진 업로드/다운로드 완료 알림
- 회원가입 하지 않은 유저
  - 카카오톡 공유하기 링크 발송

## 서비스 흐름

### 프로세스 (사진 보내는 입장)

<aside>
📍

1. 여러명이서 사진을 찍음
</aside>

<aside>
📍

2. 사진 공유하기 (갤럭시, 아이폰 동일) 버튼을 누르면 우리 앱이 목록에 뜸
</aside>

<aside>
📍

3. 우리 어플을 클릭하면 미리 생성된 방 목록이 보임

일회성 방 (방 자체의 유효기간 존재) / 영구적(유효기간 x) 방인지

영구적인 방의 경우에도 사진 클라우드는 사라짐

</aside>

<aside>
📍

4. 특정 방을 클릭하면 공유할 수 있는 페이지 (클라우드 느낌)으로 넘어감
</aside>

<aside>
📍

5. 누군가가 사진을 올리면 같은 방에 있는 다른 사용자들에게도 알람 울림

- 올라간 사진은 AI에 의해 자동분류됨
- 사진에서 인물별로 추출해주고, 거기서 선택을 하면 해당 인물이 있는 사진만 분류
</aside>

### 사진 받는 입장

<aside>
📍

6. 공유 페이지에 입장한 다른 사용자들은

1) 올라온 모든 사진들을 확인할 수 있고,
2) 특정 버튼을 누르면
3) 해당 분류 기준에 맞는 사진들이 미리보기로 보여짐
4) 다운로드 버튼을 누르면 분류된 사진이 모바일 기기 로컬 앨범에 저장됨
</aside>

<aside>
📍

7. 생성된 사진 클라우드는 24시간만 유효함 (인생네컷처럼)
</aside>

### 어플에서 직접 진입한 경우

<aside>
📍

1. 로그인 창
   회원가입 + Oauth

회원가입시 자기 사진 입력 받아야 함

</aside>

<aside>
📍

2. 2번 화면
   썸네일 및 방 정보

방 정보: 방 제목, 참여 인원

</aside>

<aside>
📍

똑같이 3,4,5,6,7번 수행 가능

</aside>

## 레퍼런스

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/0841de0b-3c20-4960-89ca-cd13ec0ad10f/86b42c2b-b525-4511-807f-4f66ec73a9e5/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/0841de0b-3c20-4960-89ca-cd13ec0ad10f/8e7d53eb-c0c3-46a3-843b-cc175eba24ba/image.png)

카톡 공유는 이런 느낌

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/0841de0b-3c20-4960-89ca-cd13ec0ad10f/27d743be-9835-48f1-953f-9debd38db4e2/image.png)
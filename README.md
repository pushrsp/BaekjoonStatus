# BaekjoonStatus

## 서버 인프라 구조
![INFRA](https://github.com/pushrsp/BaekjoonStatus/assets/58874665/f479c519-bcfe-45b1-9902-7bd130e61e02)

## CD 인프라 구조
![CICD](https://user-images.githubusercontent.com/58874665/232942166-b48fdc44-3bb9-4014-8221-22b581a15d11.png)

## 데이터베이스 관계도
![ERD](https://user-images.githubusercontent.com/58874665/227873361-ebeffda5-4dd3-434c-8c7a-165778678468.png)

## Flow
### 회원가입
![회원가입](https://user-images.githubusercontent.com/58874665/227873646-94cb849c-156b-4f8d-ae31-f64058f7a1bf.png)

### 통계 쿼리
![통계 쿼리](https://user-images.githubusercontent.com/58874665/227873771-29039fa4-dbfd-4985-93de-93022f0397ff.png)

## 외부 API
* 크롤링
  * https://www.acmicpc.net/user/{{baekjoon_username}}
  * https://github.com/tony9402/baekjoon/blob/main/picked.md 오늘의 백준 문제
* API
  * https://solved.ac/api/v3/user/show 유저 정보 [GET]
  * https://solved.ac/api/v3/problem/show 문제 정보 [GET]

## 화면 구성
### 로그인
![로그인 다크모드](https://user-images.githubusercontent.com/58874665/227870857-e046b75b-92f5-47b0-9bda-5459c9131192.png)
![로그인 라이트모드](https://user-images.githubusercontent.com/58874665/227871008-2cb0adf3-d8df-43a2-a670-ccfc271609d1.png)

### 회원가입
#### 1단계
![회원가입 1단계 다크모드](https://user-images.githubusercontent.com/58874665/227871242-63091788-c5a5-445c-ae5d-104bdfe0bf96.png)
![회원가입 1단계 라이트모드](https://user-images.githubusercontent.com/58874665/227871333-379fa733-3dfc-4ee4-8f68-556304c0bff1.png)

### 2단계
![회원가입 2단계 다크모드](https://user-images.githubusercontent.com/58874665/227872029-2d868e63-fa2f-453b-b890-82bfd4529afb.png)
![회원가입 2단계 라이트모드](https://user-images.githubusercontent.com/58874665/227872136-9ba37644-b41c-46de-b719-5d7b8c132e9a.png)

### 메인화면
#### 통계
![통계 다크모드](https://user-images.githubusercontent.com/58874665/227872382-d7ea9fae-14bc-444e-8821-ef9e0bfd3473.png)
![통계 라이트모드](https://user-images.githubusercontent.com/58874665/227872397-470077d6-17b6-4c6a-b5e2-3c3e40b508e6.png)

#### solved 문제 내역
![solved 문제 내역 다크모드](https://user-images.githubusercontent.com/58874665/227872670-dedfc7bf-149f-42cb-a8d4-e874eeea9178.png)
![solved 문제 내역 라이트모드](https://user-images.githubusercontent.com/58874665/227872742-78de2df8-c603-4aa2-8955-f1a71a17536c.png)

### 오늘의 문제
![오늘의 문제 다크모드](https://user-images.githubusercontent.com/58874665/227873099-4475bbeb-dcad-4e3c-836d-5b3265b94512.png)
![오늘의 문제 라이트모드](https://user-images.githubusercontent.com/58874665/227872908-7dada1d7-136b-4799-bc69-3f8780ec0e89.png)


## 서버 제공 API

* /auth/signup 회원가입 [POST]
* /auth/baekjoon?username 백준 아이디 체크 [GET]
* /auth/login 로그인 [POST]
* /auth/me 자동 로그인 [GET]
* /stat/daily 오늘의 문제 [GET]
* /stat/date 일별 solved 개수 [GET]
* /stat/tag 카테고리 별 solved 개수 [GET]
* /stat/level 티어 별 solved 개수 [GET]
* /stat/solved-histories 상위 티어 별 solved list [GET]

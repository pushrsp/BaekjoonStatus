# BaekjoonStatus

## 데이터베이스 관계도
![ERD](https://raw.githubusercontent.com/pushrsp/BaekjoonStatus/7139025db940ae98553509d95fed18ee3a8327f7/docs/erd.svg)

## 고민한 포인트
- [데이터베이스 insert 성능관련 문제](https://push-rsp.tistory.com/353)
- [MYSQL 데이터 중복에 관한 주관적인 생각 정리](https://push-rsp.tistory.com/355)

## 외부 API
* 크롤링
  * https://www.acmicpc.net/user/{{baekjoon_username}}
  * https://github.com/tony9402/baekjoon/blob/main/picked.md 오늘의 백준 문제
* API
  * https://solved.ac/api/v3/user/show 유저 정보 [GET]
  * https://solved.ac/api/v3/problem/show 문제 정보 [GET]


## 서버 제공 API

* /auth/signup 회원가입 [POST]
* /auth?username 아이디 중복 체크 [GET]
* /auth/baekjoon?username 백준 아이디 체크 [GET]
* /auth/login 로그인 [POST]
* /users/me 자동 로그인 [GET]
* /daily-problems 오늘의 문제 [GET]
* /stats/daily 일별 solved 개수 [GET]
* /stats/category 카테고리 별 solved 개수 [GET]
* /stats/tier 티어 별 solved 개수 [GET]
* /problems 상위 티어 별 solved list [GET]

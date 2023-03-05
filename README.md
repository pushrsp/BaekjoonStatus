# BaekjoonStatus

## 외부 API
* 크롤링
  * https://www.acmicpc.net/user/{{baekjoon_username}}
* API
  * https://solved.ac/api/v3/user/show 유저 정보 [GET]
  * https://solved.ac/api/v3/problem/show 문제 정보 [GET]


## 서버 제공 API

* /users 회원가입 [POST]
* /users/login 로그인 [POST]
* /users/me 자동 로그인 [GET]
* /daily-problems 오늘의 문제 [GET]
* /stats/daily 일별 solved 개수 [GET]
* /stats/category 카테고리 별 solved 개수 [GET]
* /stats/tier 티어 별 solved 개수 [GET]
* /problems 상위 티어 별 solved list [GET]

# 목차
- [1. 프로젝트 개요](#1-프로젝트-개요)
    - [1-1. 프로젝트 소개](#1-1-프로젝트-소개)
    - [1-2. 개발환경](#1-2-개발환경)
    - [1-3. 구현 기능](#1-3-구현-기능)
- [2. 프로젝트 설계](#2-다이어그램-및-아키텍처)
    - [2-1. ERD 다이어그램](#2-1-ERD-다이어그램)
    - [2-2. 유즈케이스 다이어그램](#2-2-유즈케이스-다이어그램)
    - [2-3. 어플리케이션 아키텍처](#2-3-어플리케이션-아키텍처)
    - [2-4. 헥사고날 아키텍처](#2-4-헥사고날-아키텍처)
- [3. 프로젝트 관리](#3-프로젝트-관리)
    - [3-1. Git을 활용한 버전 관리](#3-1-Git을-활용한-버전-관리)
    - [3-2. Postman을 활용한 API 테스트](#3-2-Postman을-활용한-API-테스트)
<br/>
<br/>

# 1. 프로젝트 개요
## 1-1. 프로젝트 소개
SK M&Service 코딩 테스트인 게시판 개발 과제입니다.

## 1.2. 개발환경
- Front-end : `Vue Js`
- Back-end : `java 17`, `Spring Boot 3.4.0`, `Spring Data Jpa`, `Spring Security`, `Gradle`, `H2 Database`, `Redis (Redisson)`, `AWS S3`

## 1-3. 구현 기능
- 로그인
- 로그아웃
- 게시판 목록 조회
    - 목록 조회 (등록일 역순)
    - 검색 조회
- 게시판 상세 조회
    - Redis Redisson Lock으로 조회수 동시성 제어
- 게시판 작성
    - 파일 첨부
        - AWS S3를 활용하여 이미지 첨부 (pdf와 같은 파일 첨부 불가)
- 게시물 수정/삭제
<br/>
<br/>

# 2. 프로젝트 설계
## 2-1. ERD 다이어그램
<img width="1091" alt="스크린샷 2024-12-03 18 55 48" src="https://github.com/user-attachments/assets/bffce87b-abfa-4314-866d-b2a5b70b7a62">
<br/>

## 2-2. 유즈케이스 다이어그램
<img width="639" alt="스크린샷 2024-12-03 18 59 46" src="https://github.com/user-attachments/assets/1ad869e7-470a-44aa-942f-76a56badfb56">
<br/>

## 2-3. 어플리케이션 아키텍처
<img width="1194" alt="스크린샷 2024-12-03 18 54 08" src="https://github.com/user-attachments/assets/77d87d89-602a-4866-ac4b-aabb0275ed42">
<br/>

## 2-4. 헥사고날 아키텍처
![스크린샷 2024-12-04 19 16 14](https://github.com/user-attachments/assets/1416e7ca-cc55-4397-bcbd-1bd4f26b79cc)
<br/>

# 3. 프로젝트 관리
## 3-1. Git을 활용한 버전 관리
<img width="1329" alt="스크린샷 2024-12-04 18 45 55" src="https://github.com/user-attachments/assets/8628daaa-f2d4-426b-82b2-6ccb304784e0">

- Git을 활용하여 각 특성에 맞는 브랜치(feat, fix, chore, refactor 등) 별로 브랜치를 생성한 후 작업 완료시 dev 브랜치에 병합합니다.
- 기능 구현 완료 후 release/0.0.1 브랜치에 dev 브랜치 작업물을 병합한 후, main 브랜치에 release/0.0.1 브랜치 작업물을 병합합니다.
- 만약 release 브랜치에서 변경 사항발생 시 dev, main 브랜치에 병합합니다.
<br/>

## 3-2. Postman을 활용한 API 테스트
<img width="1272" alt="스크린샷 2024-12-04 18 50 06" src="https://github.com/user-attachments/assets/4235d59e-a0af-408a-b790-810e145cecf1">

- 자유로운 API 테스트를 위해 Postman을 활용하여 API 테스트를 진행합니다.
- (현재 Swagger의 프로젝트 미인식 에러 발생으로 인해 사용할 수 없는 상태입니다.)
<br/>

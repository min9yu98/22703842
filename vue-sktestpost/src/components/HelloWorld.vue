<template>
  <div class="documentation">
    <!-- Header -->
    <header class="header">
      <h1>Project Documentation</h1>
    </header>

    <!-- Applicant Information -->
    <section class="applicant-info">
      <h2>지원자 기본정보</h2>
      <ul>
        <li><strong>이름:</strong> 김민규</li>
        <li><strong>생년월일:</strong> 1998.11.03</li>
        <li><strong>이메일:</strong> kimmingyu9811@gmail.com</li>
        <li><strong>최종학력:</strong> 대학(학사)</li>
        <li><strong>전공:</strong> 컴퓨터정보공학부</li>
      </ul>
    </section>

    <!-- Table of Contents -->
    <nav class="table-of-contents">
      <h2>Table of Contents</h2>
      <ul>
        <li><a href="#1">1. 프로젝트 개요</a></li>
        <li><a href="#1-1">1-1. 프로젝트 소개</a></li>
        <li><a href="#1-2">1-2. 개발환경</a></li>
        <li><a href="#1-3">1-3. 구현 기능</a></li>
        <li><a href="#2">2. 프로젝트 설계</a></li>
        <li><a href="#2-1">2-1. ERD 다이어그램</a></li>
        <li><a href="#2-2">2-2. 유즈케이스 다이어그램</a></li>
        <li><a href="#2-3">2-3. 어플리케이션 아키텍처</a></li>
        <li><a href="#2-4">2-4. 헥사고날 아키텍처</a></li>
        <li><a href="#3">3. 프로젝트 관리</a></li>
        <li><a href="#3-1">3-1. Git을 활용한 버전 관리</a></li>
        <li><a href="#3-2">3-2. Postman을 활용한 API 테스트</a></li>
      </ul>
    </nav>

    <!-- Sections -->
    <main>
      <section id="1">
        <h2>1. 프로젝트 개요</h2>
        <div>
          <h3 id="1-1">1-1. 프로젝트 소개</h3>
          <p>{{ overview.intro }}</p>
          <p>테스트 계정은 아래와 같습니다.</p>
          <ul>
            <li>사용자 1</li>
            <li>아이디: admin1</li>
            <li>비밀번호: admin1</li>
          </ul>
          
          <ul>
            <li>사용자 2</li>
            <li>아이디: admin2</li>
            <li>비밀번호: admin2</li>
          </ul>
        </div>
        <br />
        <div>
          <h3 id="1-2">1-2. 개발환경</h3>
          <ul>
            <li v-for="env in overview.environments" :key="env">{{ env }}</li>
          </ul>
        </div>
        <br />
        <div>
          <h3 id="1-3">1-3. 구현 기능</h3>
          <ul>
            <li v-for="feature in overview.features" :key="feature">{{ feature }}</li>
          </ul>
        </div>
      </section>
      <br />
      <br />
      <section id="2">
        <h2>2. 프로젝트 설계</h2>
        <div>
          <h3 id="2-1">2-1. ERD 다이어그램</h3>
          <img :src="design.erdDiagram" alt="ERD Diagram" />
        </div>
        <br />
        <div>
          <h3 id="2-2">2-2. 유즈케이스 다이어그램</h3>
          <img :src="design.useCaseDiagram" alt="Use Case Diagram" />
        </div>
        <br />
        <div>
          <h3 id="2-3">2-3. 어플리케이션 아키텍처</h3>
          <img :src="design.applicationArchitecture" alt="Application Architecture" />
        </div>
        <br />
        <div>
          <h3 id="2-4">2-4. 헥사고날 아키텍처</h3>
          <img :src="design.hexagonalArchitecture" alt="Hexagonal Architecture" />
        </div>
        <br />
        <br />
      </section>

      <section id="3">
        <h2>3. 프로젝트 관리</h2>
        <div>
          <h3 id="3-1">3-1. Git을 활용한 버전 관리</h3>
          <img :src="management.gitDiagram" alt="Git Diagram" />
          <ul>
            <li v-for="desc in management.gitDescription" :key="desc">{{ desc }}</li>
          </ul>
        </div>
        <br />
        <div>
          <h3 id="3-2">3-2. Postman을 활용한 API 테스트</h3>
          <img :src="management.postmanDiagram" alt="Postman Testing" />
          <p>{{ management.postmanDescription }}</p>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      overview: {
        intro: "SK M&Service 코딩 테스트인 게시판 개발 과제입니다.",
        environments: [
          "• Front-end: Vue Js",
          "• Back-end: Java 17, Spring Boot 3.4.0, Spring Data JPA, Spring Security, Gradle, H2 Database, Redis (Redisson), AWS S3"
        ],
        features: [
          "• 로그인",
          "• 로그아웃",
          "• 게시판 목록 조회 (등록일 역순, 검색 조회)",
          "• 게시판 상세 조회 (Redis Redisson Lock으로 조회수 동시성 제어)",
          "• 게시판 작성 (AWS S3를 활용하여 이미지 첨부, pdf와 같은 파일 첨부 불가)",
          "• 게시물 수정/삭제"
        ]
      },
      design: {
        erdDiagram: require("@/assets/erd_diagram.png"),
        useCaseDiagram: require("@/assets/usecase_diagram.png"),
        applicationArchitecture: require("@/assets/application_architecture.png"),
        hexagonalArchitecture: require("@/assets/hexagonal_architecture.png")
      },
      management: {
        gitDiagram: require("@/assets/github.png"),
        gitDescription: [
          "• Git을 활용하여 각 특성에 맞는 브랜치(feat, fix, chore, refactor 등) 별로 브랜치를 생성한 후 작업 완료시 dev 브랜치에 병합합니다.",
          "• 기능 구현 완료 후 release/0.0.1 브랜치에 dev 브랜치 작업물을 병합한 후, main 브랜치에 release/0.0.1 브랜치 작업물을 병합합니다.",
          "• 만약 release 브랜치에서 변경 사항 발생 시 dev, main 브랜치에 병합합니다."
        ],
        postmanDiagram: require("@/assets/postman.png"),
        postmanDescription:
          "자유로운 API 테스트를 위해 Postman을 활용하여 API 테스트를 진행합니다. (현재 Swagger의 프로젝트 미인식 에러 발생으로 인해 사용할 수 없는 상태입니다.)"
      }
    };
  }
};
</script>

<style scoped>
.documentation {
  font-family: Arial, sans-serif;
  line-height: 1.6;
  color: #333;
  background-color: #f9f9f9;
}

.header {
  background-color: #4caf50;
  color: white;
  padding: 20px;
  text-align: center;
}

.applicant-info {
  margin: 20px auto;
  padding: 20px;
  max-width: 800px;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.applicant-info ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.applicant-info li {
  margin: 10px 0;
  font-size: 16px;
}

.table-of-contents {
  margin: 20px auto;
  max-width: 800px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.main {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

img {
  display: block;
  margin: 20px auto;
  border: 1px solid #ccc;
  border-radius: 4px;
  max-width: 50%;
  height: auto;
}

ul {
  list-style: none;
  padding-left: 0;
}

li {
  margin: 8px 0;
  line-height: 1.6;
}

#2-1 img {
  max-width: 70%;
}

#2-2 img {
  max-width: 60%;
}

#2-3 img, #2-4 img {
  max-width: 65%;
}

#3 img {
  max-width: 50%;
}

img:hover {
  transform: scale(1.02);
  transition: transform 0.3s ease;
  cursor: pointer;
}
</style>

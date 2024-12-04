<template>
  <div class="documentation">
    <!-- Header -->
    <header class="header">
      <h1>Project Documentation</h1>
    </header>

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
        </div>
        <br/>
        <div>
          <h3 id="1-2">1-2. 개발환경</h3>
          <ul>
            <li v-for="env in overview.environments" :key="env">{{ env }}</li>
          </ul>
        </div>
        <br/>
        <div>
          <h3 id="1-3">1-3. 구현 기능</h3>
          <ul>
            <li v-for="feature in overview.features" :key="feature">{{ feature }}</li>
          </ul>
        </div>
      </section>
      <br/>
      <br/>
      <section id="2">
        <h2>2. 프로젝트 설계</h2>
        <div>
          <h3 id="2-1">2-1. ERD 다이어그램</h3>
          <img :src="design.erdDiagram" alt="ERD Diagram" />
        </div>
        <br/>
        <div>
          <h3 id="2-2">2-2. 유즈케이스 다이어그램</h3>
          <img :src="design.useCaseDiagram" alt="Use Case Diagram" />
        </div>
        <br/>
        <div>
          <h3 id="2-3">2-3. 어플리케이션 아키텍처</h3>
          <img :src="design.applicationArchitecture" alt="Application Architecture" />
        </div>
        <br/>
        <div>
          <h3 id="2-4">2-4. 헥사고날 아키텍처</h3>
          <img :src="design.hexagonalArchitecture" alt="Hexagonal Architecture" />
        </div>
        <br/>
        <br/>
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
        <br/>
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
        "• Back-end: Java 17, Spring Boot 3.4.0, Spring Data JPA, Spring Security, H2 Database, Redis (Redisson), AWS S3"
      ],
      features: [
        "• 로그인",
        "• 로그아웃",
        "• 게시판 목록 조회 (등록일 역순, 검색 조회)",
        "• 게시판 상세 조회",
        "• 게시판 작성 (파일 첨부)",
        "• 게시물 수정/삭제"
      ]
    },
      design: {
        erdDiagram: require("@/assets/erd_diagram.png"), // ERD 다이어그램
        useCaseDiagram: require("@/assets/usecase_diagram.png"), // 유즈케이스 다이어그램
        applicationArchitecture: require("@/assets/application_architecture.png"), // 어플리케이션 아키텍처
        hexagonalArchitecture: require("@/assets/hexagonal_architecture.png") // 헥사고날 아키텍처
      },
      management: {
        gitDiagram: require("@/assets/github.png"), // Git 다이어그램
        gitDescription: [
          "• Git을 활용하여 각 특성에 맞는 브랜치(feat, fix, chore, refactor 등) 별로 브랜치를 생성한 후 작업 완료시 dev 브랜치에 병합합니다.",
          "• 기능 구현 완료 후 release/0.0.1 브랜치에 dev 브랜치 작업물을 병합한 후, main 브랜치에 release/0.0.1 브랜치 작업물을 병합합니다.",
          "• 만약 release 브랜치에서 변경 사항 발생 시 dev, main 브랜치에 병합합니다."
        ],
        postmanDiagram: require("@/assets/postman.png"), // Postman 다이어그램
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

/* 기본 이미지 스타일 */
img {
  display: block;
  margin: 20px auto;
  border: 1px solid #ccc;
  border-radius: 4px;
  max-width: 50%;  /* 컨테이너의 80%로 제한 */
  height: auto;    /* 비율 유지 */
}

ul {
  list-style: none;
  padding-left: 0;
}

li {
  margin: 8px 0;
  line-height: 1.6;
}

/* 다이어그램별 특정 크기 설정 */
#2-1 img {  /* ERD 다이어그램 */
  max-width: 70%;
}

#2-2 img {  /* 유즈케이스 다이어그램 */
  max-width: 60%;
}

#2-3 img, #2-4 img {  /* 아키텍처 다이어그램 */
  max-width: 65%;
}

/* 프로젝트 관리 섹션의 이미지 */
#3 img {
  max-width: 50%;
}

/* 이미지 호버 효과 (선택사항) */
img:hover {
  transform: scale(1.02);
  transition: transform 0.3s ease;
  cursor: pointer;
}
</style>

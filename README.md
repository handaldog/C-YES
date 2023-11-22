# 🏆 C'YES - 실시간 CS 퀴즈 서비스

![Imgur](https://i.imgur.com/fTIckoF.png)


# 📌목차
- [프로젝트 진행 기간](#-프로젝트-진행-기간)
- [개요](#-개요)
- [주요 기능](#-주요-기능)
- [서비스 화면](#-서비스-화면)
- [주요 기술](#-주요-기술)
- [프로젝트 파일 구조](#-프로젝트-파일-구조)
- [프로젝트 산출물](#-프로젝트-산출물)
- [팀원 역할 분배](#-팀원-역할-분배)

<br>

# 🎞 프로젝트 진행 기간

#### - 2023.10.09(화) ~ 2023.11.17(금) (39일간 진행)

#### - SSAFY 9기 2학기 자율프로젝트

# ✨ 개요
### - CS학습의 중요성은 알고있지만 알고리즘/개발 공부하기에 바쁜 일정, 광범위하고 어려운 학습 방법은 항상 취준생들의 숙제로 남아있다.  
### - 따라서 언제 어디서나 친구와 함께하는 C-YES를 통해 문제를 해결하고자한다.

<br>

# 💻 주요 기능

### 0️⃣ 실시간 퀴즈 서비스
#### - 공지된 시간전까지만 실시간 퀴즈에 참여할 수 있다.
#### - 실시간 퀴즈가 시작되면 출제된 퀴즈를 풀 수 있다.
#### - 문제마다 20초씩 주어지며, 각각의 문제가 끝날때마다 정답을 맞춘 인원과 전체 인원이 나온다.
#### - 모든 문제가 끝나고 나면 3등까지 순위표가 나타나고 퀴즈는 끝이 난다.

<br>

### 1️⃣ 문제은행 서비스
#### - 스스로 문제들을 학습할 수 있는 페이지이다.
#### - 문제 유형은 카드학습, 객관식, O/X로 구성되어있다.
#### - 문제 카테고리는 데이터베이스, 자료구조, 디자인 패턴, 컴퓨터구조, 알고리즘, 네트워크 운영체제가 있다.

<br>

### 2️⃣ 그룹퀴즈 서비스
#### - 실시간 퀴즈 방식과 비슷하지만 원하는 사람들만 모아서 그룹퀴즈를 진행할 수 있다.
#### - 또한, 퀴즈방을 자유롭게 열어서 검색을 통해 퀴즈방에 입장할 수 있다.

<br>

# 🖼 서비스 화면



## ✅ 실시간 퀴즈
![ezgif com-crop (3)](https://github.com/handaldog/C-YES/assets/96431408/370c7467-21f2-4057-9852-c5024506d723)![ezgif com-crop (4)](https://github.com/handaldog/C-YES/assets/96431408/60de9407-04e3-4e56-9ace-560ed8fcddf8)![ezgif com-crop (5)](https://github.com/handaldog/C-YES/assets/96431408/9bf6d804-8422-4ef3-b2ce-d222984c9a44)

## ✅ 문제 은행
![ezgif com-crop (6)](https://github.com/handaldog/C-YES/assets/96431408/e5a73e42-165c-4056-a95d-fa68dbbbd4e0)![ezgif com-crop (7)](https://github.com/handaldog/C-YES/assets/96431408/dd934ae9-2b93-4b3b-9a1b-6edea8fbc6d0)![ezgif com-crop (8)](https://github.com/handaldog/C-YES/assets/96431408/76c8b271-12c8-4220-9168-2d36ebb3dfaa)

## ✅ 그룹 퀴즈
![ezgif com-crop (13)](https://github.com/handaldog/C-YES/assets/96431408/50322041-4dd0-479b-8bf4-5b02c71231a6)![ezgif com-crop (11)](https://github.com/handaldog/C-YES/assets/96431408/6fd13c9e-80a4-4f74-81e5-3043798acf42)![ezgif com-crop (12)](https://github.com/handaldog/C-YES/assets/96431408/f5260849-ad26-4b6d-9adb-9c264c553dc7)

## ✅ 관리자 문제 페이지
![ezgif com-crop (20)](https://github.com/handaldog/C-YES/assets/96431408/dd23edd2-8c74-4cc6-92ac-a87205535386)![ezgif com-crop (21)](https://github.com/handaldog/C-YES/assets/96431408/07d82308-4bf4-4160-98fd-0b4538a36640)![ezgif com-crop (22)](https://github.com/handaldog/C-YES/assets/96431408/62ea5f55-ce18-4471-84cb-a405055e0250)


# 🛠 주요 기술


**Backend**
<br>

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/fastapi-009688?style=for-the-badge&logo=fastapi&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
- Java : Oracle OpenJDK 11
- SpringBoot 2.7.17
- Spring Data Jpa 
- queryDSL 5.0.0
- Junit 4.13.1
- Gradle 7.6.1
- MySQL 서버 : latest
- MongoDB 4.4.25
- Redis 7.2.3

<br>

**FrontEnd**
<br>

<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black">&nbsp;<img src="https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=redux&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/styled components-DB7093?style=for-the-badge&logo=styledcomponents&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/node.js-339933?style=for-the-badge&logo=nodedotjs&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/typescript-3178C6?style=for-the-badge&logo=typescript&logoColor=white">&nbsp;


- React 18.2.0
- Node.js 16.16.0
- TypeScript 5.0.4
- Redux 8.0.5
- Redux-toolkit 1.9.4
- Redux-persist 6.0.0
- Styled-component 5.3.9
- Axios 1.3.5

<br>

**CI/CD**
<br>

<img src="https://img.shields.io/badge/aws ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/openssl-721412?style=for-the-badge&logo=openssl&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">&nbsp;


- AWS EC2
- Ubuntu 20.04 LTS
- Jenkins 2.414.3
- Docker Engine 24.0.5
- Nginx 1.18.0
- SSL
- Grafana latest
- Prometheus 2.44.0
- netdata

<br>

**협업 툴**
<br>

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jirasoftware&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/figma-EA4335?style=for-the-badge&logo=figma&logoColor=white">&nbsp;
- 형상 관리 : Git
- 이슈 관리 : Jira
- 커뮤니케이션 : Mattermost, Notion
- 디자인 : Figma


<br>
<br>

# 🗂 프로젝트 파일 구조

### Backend

```
webserver
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂cyes
 ┃ ┃ ┃ ┃ ┗ 📂webserver
 ┃ ┃ ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂adminQuiz
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂Answer
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂problem
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂quiz
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂quizproblem
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂quizrank
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂rank
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂answer
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂end
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂problem
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂result
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂start
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂submit
 ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┣ 📂interceptor
 ┃ ┃ ┃ ┃ ┃ ┣ 📂redis
 ┃ ┃ ┃ ┃ ┃ ┣ 📂utils
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂jwt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂oauth
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂enums
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂token
 ┃ ┗ 📂resources
 ┗ 
 
socket-server
📦src
 ┣ 📂main
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂cyes
 ┃ ┃ ┃ ┃ ┗ 📂socketserver
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┣ 📂stomp
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂chat
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂enter
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂submit
 ┗ 📂test

```

### FrontEnd

```
📦src
 ┣ 📂api
 ┃ ┣ 📂websocket
 ┣ 📂components
 ┃ ┣ 📂bottomnav
 ┃ ┣ 📂button
 ┃ ┣ 📂chat
 ┃ ┣ 📂dropdown
 ┃ ┣ 📂modal
 ┣ 📂font
 ┣ 📂pages
 ┃ ┣ 📂adminquiz
 ┃ ┣ 📂cs
 ┃ ┃ ┣ 📂types
 ┃ ┣ 📂group
 ┃ ┣ 📂live
 ┃ ┣ 📂login
 ┃ ┗ 📂quiz
 ┣ 📂redux
 ┃ ┣ 📂actions
 ┃ ┣ 📂reducers
 ┣ 📂types
 ┣ 📜.env
 ┣ 📜App.css
 ┣ 📜App.test.tsx
 ┣ 📜App.tsx
 ┣ 📜index.css
 ┣ 📜index.tsx
 ┣ 📜logo.svg
 ┣ 📜react-app-env.d.ts
 ┣ 📜reportWebVitals.ts
 ┗ 📜setupTests.ts
```

<br>
<br>


# 📋 프로젝트 산출물

- [API 명세서](https://opalescent-appliance-551.notion.site/API-c9cdccd9338e409698efd89e495d1052?pvs=4)
- [ERD](https://www.erdcloud.com/d/aReBoHxvGA84SdeaT)
- [와이어프레임](https://www.figma.com/file/EHttBkzorg0ZYZp0YbVavo/%EA%B2%BD%EC%B6%95%EB%B9%84-c-yes?type=design&node-id=303-2&mode=design)
- [시스템 아키텍처](https://www.canva.com/design/DAF0VGnfH20/yiLXKm1KIPTV1KpNKnaTkw/edit)




<br>
<br>

# 👩‍💻 팀원 역할 분배

| 우승빈            | 배수빈 | 우수인   | 유혜빈  | 유태영  | 조준희  |
| ----------------- | -------- | -------- | ------- | ------- | ------- |
| <img src="https://github.com/handaldog/C-YES/assets/96431408/973a0781-2e83-40e0-a0df-e07f8a72a3e7" width="130" height="180">|<img src="https://github.com/handaldog/C-YES/assets/96431408/7402e39d-0b01-41d4-86fe-87076775e417" width="130" height="180">|<img src="https://github.com/handaldog/C-YES/assets/96431408/34b2c650-8e5e-49a6-94b1-080e61429578" width="130" height="180">|<img src="https://github.com/handaldog/C-YES/assets/96431408/efaaad7e-09b2-48d6-a790-88af70e42965" width="130" height="180">|<img src="https://github.com/handaldog/C-YES/assets/96431408/d3efc9c8-3a18-4713-bab2-37bec4561c67" width="130" height="180">|<img src="https://github.com/handaldog/C-YES/assets/96431408/c0e8b853-369c-4e5c-b370-757761c804e3" width="130" height="180">|
| Leader & Backend | Frontend | Frontend&Infra | Infra & Backend | Backend | Backend |

<br>
<br>

# Code Circle 소개 

### **1. 개요 (Overview)**

### 1.1 프로젝트 배경 및 목적

혼자 공부할 때보다 다른 사람과 코드를 공유하고 직접 대화하는 과정에서 이해가 더 잘되고 기억에 오래 남는다는 것을 경험했습니다. 특히 내 코드를 설명하거나 피드백을 받는 과정이 실력 향상에 도움이 되었습니다.

이런 경험을 바탕으로, 자연스럽게 코드 리뷰와 의견 교환이 이루어질 수 있는 플랫폼을 만들고자 했습니다. 기존처럼 흩어진 툴을 사용하는 대신, 코드 편집, 대화, 프로젝트 관리, 커뮤니티 기능을 하나로 통합한 환경을 제공하여 개발자가 코드에 집중할 수 있도록 하는 것이 목표입니다.

### 1.2 프로젝트 범위

- **사용자 관리**: 일반 로그인 및 얼굴 인식을 통한 가입/로그인, 마이페이지, 계정 관리
- **실시간 협업 (라이브룸)**: WebSocket 기반 코드 동기화, 채팅, 사용자 상태 표시
- **커뮤니티**: 게시판, 댓글(일반/코드 라인), 좋아요 기능
- **그룹 및 프로젝트 관리**: 그룹 생성/초대, 마일스톤 및 태스크 관리
- **AI 서버**: Python Flask + DeepFace를 이용한 얼굴 임베딩 및 사용자 식별 API

---

### **2. 시스템 아키텍처 (System Architecture)**

### **2.1. 전체 시스템 구성도**

Spring Boot 백엔드 서버와 Python Flask 기반 AI 서버를 분리하여, 안정적인 비즈니스 로직과 얼굴 인식 기능을 각각 처리합니다. 서버 간 통신은 REST API로 이루어집니다.

![image.png](image.png)

---

**2.2. 주요 기술 스택**

| 구분 | 기술 | 역할 |
| --- | --- | --- |
| 백엔드 | `Java 17 / Spring Boot 3.x` | API 및 비즈니스 로직 처리 |
| DB 통신 | `Spring Data JPA` | ORM 기반 데이터 액세스 |
| 실시간 통신 | `Spring WebSocket / SockJS / STOMP` | 코드, 채팅 등 실시간 기능 구현 |
| AI 서버 | `Python / Flask / DeepFace` | 얼굴 인식 및 유사도 분석 |
| 프론트엔드 | `HTML/CSS/JS + Mustache` | UI 구성 및 서버 데이터 렌더링 |
| 데이터베이스 | `Oracle` | 사용자 및 협업 관련 데이터 저장 |

### **3. 핵심 기능 및 구현**

### **3.1. AI 얼굴 인식 기능**

- **구현 내용**: 사용자는 회원가입 시 자신의 얼굴을 등록할 수 있으며, 로그인 시 아이디/비밀번호 대신 얼굴 인증만으로 로그인이 가능합니다.
- **동작 원리**:
    1. **(Spring)** `MemberController`는 브라우저로부터 얼굴 이미지(Base64)를 수신합니다.
    2. **(Spring)** `RestTemplate`을 사용하여 Python Flask AI 서버의 API (`/generate-embedding` 또는 `/find-user-by-face`)를 호출하며 이미지 데이터를 전달합니다.
    3. **(Python)** Flask 서버는 `DeepFace` 라이브러리를 이용해 전달받은 이미지에서 얼굴 특징(512차원 벡터 임베딩)을 추출합니다.
    4. **(Python)** 로그인 시에는 추출된 특징과 DB의 모든 특징을 코사인 유사도로 비교하여 가장 유사한 사용자를 찾고, 임계값(0.4) 이하일 경우 동일인으로 판정합니다.
    5. **(Python → Spring)** AI 서버는 분석 결과를 JSON 형태로 Spring 서버에 반환합니다.
    6. **(Spring)** `MemberController`는 반환된 결과를 바탕으로 회원가입 또는 로그인 절차를 최종 마무리합니다.

---

### **3.2. 실시간 협업 기능 (라이브룸)**

- **구현 내용**: 여러 사용자가 하나의 '라이브룸'에 접속하여 실시간으로 코드를 함께 편집하고, 채팅을 나눌 수 있습니다.
- **기술 구현**:
    - **WebSocket**: 서버와 클라이언트 간의 지속적인 양방향 연결을 위해 Spring WebSocket과 SockJS를 사용했습니다. (`WebSocketConfig.java`)
    - **STOMP**: WebSocket 위에서 동작하는 메시징 프로토콜로, '구독/발행(Pub/Sub)' 모델을 쉽게 구현할 수 있도록 돕습니다.
    - **동작 흐름**:
        1. 클라이언트는 `/ws-stomp` 엔드포인트로 WebSocket 을 연결합니다 .
        2. 특정 라이브룸에 해당하는 채널(예: `/topic/room/{roomId}`)을 구독합니다.
        3. 사용자가 코드를 수정하거나 채팅 메시지를 보내면, 클라이언트는 해당 내용을 `/app/code/update/{roomId}`와 같은 목적지로 전송(발행)합니다.
        4. `LiveRoomController`의 `@MessageMapping` 어노테이션이 붙은 메소드가 메시지를 수신합니다.
        5. `@SendTo` 어노테이션에 의해, 서버는 해당 메시지를 같은 채널을 구독 중인 모든 클라이언트에게 재전송(Broadcast)합니다.
        6. 각 클라이언트는 수신한 메시지를 바탕으로 자신의 화면을 실시간으로 업데이트합니다.

---

### 4. 데이터베이스 설계

### 4.1. ERD

아래는 주요 테이블간의 관계를 요약하여 나타냈습니다.

![image.png](image%201.png)

### 4.2. 주요 테이블 명세

| 테이블명 | 설명 | 주요 컬럼 |
| --- | --- | --- |
| CC_MEMBER | 사용자 정보 | ID(PK), PASSWORD, NICKNAME(UK), EMAIL(UK), TYPE(사용자/관리자), FACE_EMBEDDING(CLOB) |
| CC_BOARD | 게시글 정보 | BOARD_NO(PK), WRITER_ID(FK), GROUP_NO(FK), PARENT_BOARD_NO(FK), BOARD_TYPE(질문/답변 등) |
| CC_GROUP | 사용자들이 생성한 그룹 정보 | GROUP_NO(PK), GROUP_NAME, CREATOR_ID(FK) |
| CC_GROUP_MEMBER | 그룹에 있는 사용자 정보 | GROUP_NO(FK), MEMBER_ID(FK), TYPE(그룹장/멤버) |
| CC_LIVE_ROOM | 실시간 코드 방 정보 | ROOM_ID(PK), ROOM_NAME, CREATOR_ID(FK) |
| CC_LIVE_ROOM_MEMBER | 실시간 코드 방의 참가한 멤버 정보 | ROOM_ID(FK), MEMBER_ID(FK) |
| CC_MILESTONE | 프로젝트의 마일스톤 정보 | MILESTONE_ID(PK), GROUP_NO(FK), TITLE, STATUS |
| CC_TASK | 마일스톤에 속한 할 일 정보 | TASK_ID(PK), MILESTONE_ID(FK), ASSIGNEE_ID(FK), STATUS |
| CC_COMMENT | 게시글에 대한 일반 댓글 | COMMENT_NO(PK), BOARD_NO(FK), WRITER_ID(FK) |
| CC_CODE_COMMENT | 코드 라인에 대한 댓글 | COMMENT_NO(PK), BOARD_NO(FK), MEMBER_ID(FK), LINE_NUMBER |
| CC_LIKE | 게시글에 대한 '좋아요' 정보 | LIKE_NO(PK), BOARD_NO(FK), MEMBER_ID(FK) |
| CC_GROUP_INVITE | 그룹 초대 정보 | INVITE_NO(PK), GROUP_NO(FK), INVITER_ID(FK), INVITEE_ID(FK) |
| CC_LIVE_ROOM_INVITE | 실시간 코드방 초대 정보 | INVITE_NO(PK), ROOM_ID(FK), INVITER_ID(FK), INVITEE_ID(FK) |

## 5. 결론  & 향후 개선

### 5.1 프로젝트를 하면서 느낀점

처음에는 모르는 것이 많아 웹 소켓이나 스프링 MVC 패턴이 생소하게 느껴졌습니다. 지금도 현재 프로젝트의 모든 걸 잘 안다고 는 할 수 없지만, 프로젝트를 만들면서 직접 부딪히고 배우는 과정에서 하나 씩 익혀나갔습니다. 아직도 기능을 만드는 일은 쉽지 않지만, 필요하다고 생각되는 부분은 계속 도전하며 구현해보고 있습니다. 따라서 이런 과정 자체가 저에게 큰 경험이 될 것 이라고 생각합니다

### 5.2 향후 개선 방향

- java와 Python 코드에 대한 자동화된 테스트 및 실행 검증 환경 구축
- 코드 파일 업로드를 통한 아키텍처 검증 서비스
- 사용자 피드백 기반 UX 개선 및 개인화 기능 도입

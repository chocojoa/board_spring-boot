# 게시판 Backend (Spring Boot)

개인 학습 목적으로 제작한 Spring Boot 기반 게시판 REST API 서버입니다.
Vue.js / React.js 프론트엔드와 연동하여 사용할 수 있습니다.

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.13 |
| ORM | MyBatis 4.0.1 |
| Security | Spring Security + JWT (JJWT 0.13.0) |
| Database | MySQL 8 |
| Cache | Redis, Caffeine |
| Build | Gradle 8.11.1 |
| Deploy | Docker |

---

## 주요 기능

- **회원 인증**: 회원가입, 로그인, 로그아웃, AccessToken 재발행
- **JWT 인증**: HttpOnly 쿠키 기반 AccessToken / RefreshToken 관리
- **RBAC 접근 제어**: 권한(Role) → 메뉴 → API URL 기반 접근 제어
- **관리자 기능**: 사용자, 권한, 메뉴 CRUD 및 매핑 관리
- **게시판**: 카테고리별 게시글 및 댓글 CRUD
- **공통 기능**: 프로필 수정, 사용자 메뉴 조회, 브레드크럼 조회
- **성능 측정**: AOP 기반 Controller 메서드 실행 시간 측정
- **캐싱**: Redis(RefreshToken, 조회수), Caffeine(메뉴 URL)

---

## 프로젝트 구조

```
src/main/java/com/lollipop/board/
├── auth/                    # 인증 (로그인, 회원가입, 토큰 재발행)
├── admin/
│   ├── user/                # 사용자 관리
│   ├── role/                # 권한 관리 (Role, UserRole, MenuRole)
│   └── menu/                # 메뉴 관리
├── post/                    # 게시글 및 댓글
├── common/                  # 공통 (프로필, 사용자 메뉴, 에러 처리)
└── setup/
    ├── jwt/                 # JWT 토큰 생성/검증/필터
    ├── security/            # Spring Security 설정
    ├── redis/               # Redis 설정 및 DAO
    ├── config/              # Caffeine 캐시 설정, WebMVC 설정
    ├── interceptor/         # 메뉴 접근 권한 인터셉터
    ├── aspect/              # 성능 측정 AOP
    ├── advice/              # 전역 예외 처리
    └── model/               # 공통 응답 모델 (ApiResponse)

src/main/resources/
├── application.yml
└── mybatis/mapper/          # MyBatis XML Mapper (9개)
```

---

## API 엔드포인트

### 인증 (`/api/auth`) - 인증 불필요

| 메서드 | URL | 설명 |
|--------|-----|------|
| POST | `/api/auth/signUp` | 회원가입 |
| POST | `/api/auth/signIn` | 로그인 |
| POST | `/api/auth/reissue` | AccessToken 재발행 |
| POST | `/api/auth/signOut` | 로그아웃 |

### 사용자 관리 (`/api/admin/users`)

| 메서드 | URL | 설명 |
|--------|-----|------|
| GET | `/api/admin/users` | 사용자 목록 조회 (페이지네이션) |
| GET | `/api/admin/users/{userId}` | 사용자 상세 조회 |
| POST | `/api/admin/users` | 사용자 생성 |
| PUT | `/api/admin/users/{userId}` | 사용자 수정 |

### 권한 관리 (`/api/admin/roles`)

| 메서드 | URL | 설명 |
|--------|-----|------|
| GET | `/api/admin/roles` | 권한 목록 조회 |
| GET | `/api/admin/roles/{roleId}` | 권한 상세 조회 |
| POST | `/api/admin/roles` | 권한 생성 |
| PUT | `/api/admin/roles/{roleId}` | 권한 수정 |
| DELETE | `/api/admin/roles/{roleId}` | 권한 삭제 |

### 메뉴 관리 (`/api/admin/menus`)

| 메서드 | URL | 설명 |
|--------|-----|------|
| GET | `/api/admin/menus` | 메뉴 목록 조회 |
| GET | `/api/admin/menus/{menuId}` | 메뉴 상세 조회 |
| POST | `/api/admin/menus` | 메뉴 생성 |
| PUT | `/api/admin/menus/{menuId}` | 메뉴 수정 |
| DELETE | `/api/admin/menus/{menuId}` | 메뉴 삭제 |

### 권한-메뉴 / 권한-사용자 매핑

| 메서드 | URL | 설명 |
|--------|-----|------|
| GET | `/api/admin/menuRole/{roleId}` | 권한별 메뉴 목록 조회 |
| POST | `/api/admin/menuRole/{roleId}` | 권한에 메뉴 할당 |
| GET | `/api/admin/userRole/{roleId}` | 권한별 사용자 목록 조회 |
| POST | `/api/admin/userRole/{roleId}` | 사용자에게 권한 할당 |

### 게시판 (`/api/boards`)

| 메서드 | URL | 설명 |
|--------|-----|------|
| GET | `/api/boards/{categoryId}/posts` | 게시글 목록 조회 |
| GET | `/api/boards/{categoryId}/posts/{postId}` | 게시글 상세 조회 (조회수 증가) |
| POST | `/api/boards/{categoryId}/posts` | 게시글 작성 |
| PUT | `/api/boards/{categoryId}/posts/{postId}` | 게시글 수정 |
| GET | `/api/boards/{categoryId}/posts/{postId}/comments` | 댓글 목록 조회 |
| POST | `/api/boards/{categoryId}/posts/{postId}/comments` | 댓글 작성 |

### 공통 (`/api/common`)

| 메서드 | URL | 설명 |
|--------|-----|------|
| GET | `/api/common/menus/{userId}` | 사용자 메뉴 목록 조회 |
| GET | `/api/common/menus/breadcrumbs` | 메뉴 브레드크럼 조회 |
| PUT | `/api/common/profile/{userId}` | 프로필 수정 |

---

## 인증 흐름

```
[로그인]
POST /api/auth/signIn
  → AuthenticationManager 인증 (BCrypt 비밀번호 검증)
  → JWT 생성 (AccessToken: 1시간, RefreshToken: 10일)
  → RefreshToken Redis 저장
  → 응답 쿠키에 accessToken, refreshToken 설정 (HttpOnly, Secure, SameSite=Strict)

[API 요청]
Request (쿠키: accessToken)
  → JwtAuthenticationFilter: 쿠키에서 토큰 추출 및 검증
  → SecurityContext에 Authentication 설정
  → MenuAuthorizationInterceptor: 사용자 권한 기반 API 접근 허용 여부 확인
  → Controller 처리

[토큰 재발행]
POST /api/auth/reissue
  → 쿠키의 refreshToken 검증
  → Redis에서 저장된 RefreshToken 비교
  → 새 AccessToken, RefreshToken 발급
```

---

## 메뉴 접근 권한 제어 (RBAC)

```
User → UserRole → Role → MenuRole → Menu → MenuApi → API URL
```

사용자에게 권한(Role)을 부여하고, 권한에 메뉴를 매핑하며, 메뉴에 API URL을 매핑합니다.
API 요청 시 인터셉터가 해당 사용자가 접근 가능한 URL 목록을 조회하여 허용 여부를 판단합니다.
접근 가능한 URL 목록은 Caffeine 캐시(이메일 기반, 1시간 유지)로 성능을 최적화합니다.

---

## DB 테이블 구조

| 테이블 | 설명 |
|--------|------|
| `user` | 사용자 (email, password, user_name) |
| `role` | 권한 (role_name, description) |
| `user_role` | 사용자-권한 매핑 |
| `menu` | 메뉴 (계층 구조, sort_order, usage_status) |
| `menu_role` | 권한-메뉴 매핑 |
| `menu_api` | 메뉴-API URL 매핑 |
| `post` | 게시글 (category_id, title, content, view_count) |
| `post_comment` | 댓글 (post_id, parent_comment_id) |

DB 초기 스키마는 `ERD_board.sql` 파일을 참고하세요.

---

## API 응답 형식

**성공 응답**
```json
{
  "data": { },
  "timestamp": "2024-12-03T00:00:00",
  "processingTimeMs": 45
}
```

**에러 응답**
```json
{
  "message": "에러 메시지",
  "requestUrl": "/api/...",
  "method": "POST",
  "errorList": [
    {
      "field": "email",
      "message": "이메일 형식이 올바르지 않습니다.",
      "rejectedValue": "invalid-email"
    }
  ],
  "timestamp": "2024-12-03T00:00:00"
}
```

---

## 필요 환경

- Java 21
- MySQL 8
- Redis

---

## 로컬 실행 방법

### 1. MySQL 데이터베이스 생성

```sql
CREATE DATABASE board CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'lollipop'@'localhost' IDENTIFIED BY '!qwer1234';
GRANT ALL PRIVILEGES ON board.* TO 'lollipop'@'localhost';

-- 스키마 적용
SOURCE ERD_board.sql;
```

### 2. Redis 실행

```bash
redis-server
```

### 3. 애플리케이션 설정 (`src/main/resources/application.yml`)

필요 시 DB 접속 정보, Redis 호스트/포트, JWT 시크릿 키를 수정합니다.

### 4. 빌드 및 실행

```bash
./gradlew bootRun
```

---

## Docker 실행

```bash
# 빌드
./gradlew build
docker build -t board-backend .

# 실행
docker run -p 8080:8080 board-backend
```

---

## 주요 설정값

| 설정 | 값 |
|------|----|
| AccessToken 만료 | 3600초 (1시간) |
| RefreshToken 만료 | 864000초 (10일) |
| JWT 알고리즘 | HS512 |
| Caffeine 캐시 유지 시간 | 1시간 (최대 1,000건) |
| MySQL 포트 | 3306 |
| Redis 포트 | 6379 |

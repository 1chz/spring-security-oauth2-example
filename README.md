# Spring Security OAuth2 예제

이 저장소는 Spring Security OAuth2 Client 모듈을 사용하여 클라이언트와 서버 간에 인증 및 권한 부여를 설정하는 방법을 보여주는 간단한 예제입니다.

## 요구 사항

- Java 17 이상
- Gradle

## 실행 방법

1. 저장소를 클론합니다.

```shell
git clone https://github.com/olivahn/spring-security-oauth2-example.git
```

2. `application-oauth.yaml`의 `client-id`와 `client-secret`을 직접 입력하거나, 환경변수로 지정합니다. (아래 표기된 값만 설정해주면 되며, 다른 값은 건들지 마세요.)

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: ${GITHUB_CLIENT_ID}
            client-secret: ${GITHUB_CLIENT_SECRET}

          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}

          naver:
            client-id: ${NAVER_CLIENT_ID}
            client-secret: ${NAVER_CLIENT_SECRET}

          kakao:
            client-id: ${KAKAO_CLIENT_ID}
            client-secret: ${KAKAO_CLIENT_SECRET}
```

3. 애플리케이션을 빌드하고 실행합니다.

```shell
chmod +x ./gradlew && ./gradlew bootRun 
```

4. 웹 브라우저에서 http://localhost:8080/ 으로 접속하면 로그인 페이지가 표시됩니다.

<img width="398" alt="image" src="https://user-images.githubusercontent.com/71188307/221410026-c88e40b6-a93a-4beb-b20e-e1632ec9a61b.png">

## API

- http://localhost:8080/login - 로그인
- http://localhost:8080/logout - 로그아웃
- http://localhost:8080/users - IDP에서 얻어온 사용자 정보 조회
- http://localhost:8080/h2-console - H2 DB 콘솔 (비밀번호 없음)

<img width="464" alt="image" src="https://user-images.githubusercontent.com/71188307/221410164-eba0ebb9-a9a5-46e7-8b6f-16ce5836c69f.png">

## 라이선스

MIT 라이선스에 따라 사용할 수 있습니다. 자세한 내용은 [LICENSE](./LICENSE) 파일을 참조하세요.
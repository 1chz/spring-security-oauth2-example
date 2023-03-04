# Spring Security OAuth2 예제

이 저장소는 `Spring Security OAuth2 Client 모듈`을 사용하여 클라이언트와 서버 간에 인증 및 권한 부여를 설정하는 방법을 보여주는 간단한 예제입니다.

<img width="1570" alt="image" src="https://user-images.githubusercontent.com/71188307/222906211-09b7f2eb-5fa2-4b5c-86b4-b217aeff5a29.png">

## 요구 사항

- Java 17 이상
- Gradle

## 실행 방법

1. 저장소를 클론합니다.

```shell
git clone https://github.com/olivahn/spring-security-oauth2-example.git
```

2. 스프링 시큐리티에서 기본적으로 제공하는 `CommonOAuth2Provider`는 잘 알려진 IDP인 `구글`, `깃허브`, `페이스북`, `옥타`의 정보들을 담고있으며, 이 외의 IDP들은 직접 `registration`과 `provider` 항목을 작성해주어야 합니다. 본 예제 코드에서는 네이버, 카카오의 정보를 직접 작성해두었으니 `application-oauth.yaml`를 열으셔서 `client-id`와 `client-secret`만 직접 입력하거나, 환경변수로 지정하세요. 또한, 예를 들어 `애플`에 대해 작성하고 테스트하기 싫다면 `애플`의 `registration`과 `provider`를 주석처리하거나 제거하시면 됩니다. (작성하지도 않고, 주석처리 or 제거또한 하지 않는다면 서버가 기동되지 않습니다.)

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          # 아래의 항목들만 하드코딩하거나, 환경변수로 지정합니다. (다른 항목들은 임의로 수정하지 마세요.)
          github:
            client-id: ${GITHUB_CLIENT_ID}
            client-secret: ${GITHUB_CLIENT_SECRET}

          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}

          apple:
            client-id: ${APPLE_CLIENT_ID}
            client-secret: ${APPLE_CLIENT_SECRET}

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

5. OAuth2 로그인에 성공하면 액세스 토큰을 포함한 채 `http://localhost:3000/tokens`을 향해 리디렉션됩니다. 리디렉션 URI를 바꾸고 싶다면, `http://localhost:8080/oauth2/authorization/{{ provider }}?redirect_uri={{ redirect_uri }}`와 같이 요청하면 됩니다. 예: `http://localhost:8080/oauth2/authorization/github?redirect_uri=http://localhost:3000/some_uri`
<img width="1047" alt="image" src="https://user-images.githubusercontent.com/71188307/222904354-7a85cb6c-5142-475a-8405-c9660979edd5.png">


## API

- http://localhost:8080/login - 로그인
- http://localhost:8080/users - 현재 토큰의 사용자 정보 조회
- http://localhost:8080/h2-console - H2 DB 콘솔 (비밀번호 없음)

<img width="464" alt="image" src="https://user-images.githubusercontent.com/71188307/221410164-eba0ebb9-a9a5-46e7-8b6f-16ce5836c69f.png">

## 라이선스

MIT 라이선스에 따라 사용할 수 있습니다. 자세한 내용은 [LICENSE](./LICENSE) 파일을 참조하세요.

package io.github.olivahn.oauth2.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.SerializationUtils;

/**
 * <p>
 * 세션 생성 전략을 SessionCreationPolicy.STATELESS로 사용하므로 기본적으로 사용되는 AuthorizationRequestRepository<OAuth2AuthorizationRequest>의 구현체인 HttpSessionOAuth2AuthorizationRequestRepository를 사용할 수 없음. AuthorizationRequest의 Context를 저장하기 위해 Redis나 RDB같은 외부 저장소를
 * 사용하거나, Short Lived HTTP Cookie를 사용할 수 있는데, 비용상 Cookie가 더 저렴하다고 판단하여 Cookie를 사용함.
 * </p>
 */
final class HttpCookieOAuth2AuthorizationRequest implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public static final String REDIRECT_URI = "redirect_uri";

    public static final String OAUTH2_AUTHORIZATION_REQUEST = "oauth2_authorization_request";

    public static final int COOKIE_EXPIRY_SECONDS = 300;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(Objects::nonNull)
                .filter(cookie -> OAUTH2_AUTHORIZATION_REQUEST.equals(cookie.getName()))
                .findFirst()
                .map(this::deserialize)
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            this.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST);
            this.deleteCookie(request, response, REDIRECT_URI);
            return;
        }

        String serialized = this.serialize(authorizationRequest);
        Cookie oauth2RequestCookie = this.createHttpOnlyCookie(OAUTH2_AUTHORIZATION_REQUEST, serialized);
        response.addCookie(oauth2RequestCookie);

        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI);
        if (redirectUriAfterLogin == null || redirectUriAfterLogin.isBlank()) {
            return;
        }
        Cookie redirectUriCookie = this.createHttpOnlyCookie(REDIRECT_URI, redirectUriAfterLogin);
        response.addCookie(redirectUriCookie);
    }

    private void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Arrays.stream(request.getCookies())
                .filter(Objects::nonNull)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .forEach(cookie -> {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                });
    }

    private Cookie createHttpOnlyCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRY_SECONDS);
        return cookie;
    }

    private String serialize(Object object) {
        byte[] serialized = SerializationUtils.serialize(object);
        return Base64.getUrlEncoder().encodeToString(serialized);
    }

    private OAuth2AuthorizationRequest deserialize(Cookie cookie) {
        String value = cookie.getValue();
        byte[] decode = Base64.getUrlDecoder().decode(value);
        Object deserialize = SerializationUtils.deserialize(decode);
        return (OAuth2AuthorizationRequest) deserialize;
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        // NOTE:
        // 저장되기전의 OAuth2AuthorizationRequest와 이 시점에서 request에서 꺼낸 OAuth2AuthorizationRequest를 비교해 다르다면 null을 반환하는게 보안상 유리하다.
        // 세부 구현은 HttpSessionOAuth2AuthorizationRequestRepository#removeAuthorizationRequest 참고
        return this.loadAuthorizationRequest(request);
    }
}

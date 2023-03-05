package io.github.olivahn.oauth2.model;

import io.github.olivahn.oauth2.repository.Authorities;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * OAuth2 제공자에서 제공하는 유저 정보를 추상화한 인터페이스입니다.
 *
 * @author olivahn
 */
public interface OAuth2ProvidersUser {
    /**
     * OAuth2 제공자(구글, 깃허브, 옥타 등)의 이름을 반환합니다.
     *
     * @return OAuth2 제공자 이름
     */
    String provider();

    /**
     * OAuth2 제공자에서 제공하는 유저의 고유한 식별자를 반환합니다. 없다면 null을 반환합니다.
     *
     * @return OAuth2 제공자에서 제공하는 유저의 고유한 식별자
     */
    String id();

    /**
     * OAuth2 제공자에서 제공하는 유저의 이름을 반환합니다. 없다면 null을 반환합니다.
     *
     * @return OAuth2 제공자에서 제공하는 유저의 이름
     */
    String username();

    /**
     * OAuth2 제공자에서 제공하는 유저의 이메일을 반환합니다. 없다면 null을 반환합니다.
     *
     * @return OAuth2 제공자에서 제공하는 유저의 이메일
     */
    String email();

    /**
     * OAuth2 제공자에서 유효한 유저의 권한을 반환합니다. 최소 1개 이상의 권한이 있어야 합니다.
     *
     * @return OAuth2 제공자에서 유효한 유저의 권한
     */
    List<Authorities> authorities();

    /**
     * OAuth2 제공자에서 제공하는 유저의 추가 정보를 반환합니다. 없다면 null을 반환합니다.
     *
     * @return OAuth2 제공자에서 제공하는 유저의 추가 정보
     */
    Map<String, Object> attributes();

    static OAuth2ProvidersUser from(Authentication authentication) {
        String clientRegistrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        return OAuth2ProvidersUser.of(clientRegistrationId, (OAuth2User) authentication.getPrincipal());
    }

    static OAuth2ProvidersUser of(String clientRegistrationId, OAuth2User oAuth2User) {
        return switch (OAuth2Providers.from(clientRegistrationId)) {
            case GOOGLE -> new GoogleOAuth2ProvidersUser(oAuth2User, clientRegistrationId);
            case GITHUB -> new GitHubOAuth2ProvidersUser(oAuth2User, clientRegistrationId);
            case NAVER -> new NaverOAuth2ProvidersUser(oAuth2User, clientRegistrationId);
            case KAKAO -> new KakaoOAuth2ProvidersUser(oAuth2User, clientRegistrationId);
        };
    }
}

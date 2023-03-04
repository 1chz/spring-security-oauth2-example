package io.github.olivahn.oauth2.model;

import io.github.olivahn.oauth2.repository.Authorities;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IdentityProvidersUser {
    String getProvider();

    String getId();

    String getUsername();

    String getEmail();

    List<Authorities> getAuthorities();

    Map<String, Object> getAttributes();

    static IdentityProvidersUser from(Authentication authentication) {
        String clientRegistrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        return IdentityProvidersUser.from(clientRegistrationId, (OAuth2User) authentication.getPrincipal());
    }

    static IdentityProvidersUser from(String clientRegistrationId, OAuth2User oAuth2User) {
        return switch (IdentityProviders.from(clientRegistrationId)) {
            case GOOGLE -> new GoogleIdentityProvidersUser(oAuth2User, clientRegistrationId);
            case GITHUB -> new GitHubIdentityProvidersUser(oAuth2User, clientRegistrationId);
            case NAVER -> new NaverIdentityProvidersUser(oAuth2User, clientRegistrationId);
            case KAKAO -> new KakaoIdentityProvidersUser(oAuth2User, clientRegistrationId);
        };
    }
}

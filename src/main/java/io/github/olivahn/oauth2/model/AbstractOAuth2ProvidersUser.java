package io.github.olivahn.oauth2.model;

import io.github.olivahn.oauth2.repository.Authorities;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * OAuth2 제공자에서 제공하는 유저 정보를 일부 구현한 추상 클래스입니다. 예를 들어 `email`이나 `password` 등은 대부분의 OAuth2 제공자들이 동일한 이름으로 제공하기 때문에 추상 클래스에서 구현할 수 있습니다.
 */
abstract class AbstractOAuth2ProvidersUser implements OAuth2ProvidersUser {
    private final String clientRegistrationId;
    private final Map<String, Object> attributes;
    private final List<Authorities> authorities;

    protected AbstractOAuth2ProvidersUser(OAuth2User oAuth2User, String clientRegistrationId, Map<String, Object> attributes) {
        this.clientRegistrationId = clientRegistrationId;
        this.attributes = attributes;
        this.authorities = oAuth2User.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(Authorities::new)
                .toList();
    }

    @Override
    public String email() {
        return (String) attributes().get("email");
    }

    @Override
    public String provider() {
        return this.clientRegistrationId;
    }

    @Override
    public List<Authorities> authorities() {
        return new ArrayList<>(authorities);
    }

    @Override
    public Map<String, Object> attributes() {
        return attributes;
    }
}
